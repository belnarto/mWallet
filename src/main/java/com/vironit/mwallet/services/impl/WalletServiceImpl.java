package com.vironit.mwallet.services.impl;

import com.vironit.mwallet.dao.WalletDao;
import com.vironit.mwallet.models.dto.CurrencyDto;
import com.vironit.mwallet.models.dto.UserDto;
import com.vironit.mwallet.models.dto.WalletDto;
import com.vironit.mwallet.services.CurrencyService;
import com.vironit.mwallet.services.WalletService;
import com.vironit.mwallet.services.exception.WalletServiceException;
import com.vironit.mwallet.services.exception.WalletStatusException;
import com.vironit.mwallet.models.entity.User;
import com.vironit.mwallet.models.entity.Wallet;
import com.vironit.mwallet.models.attributes.WalletStatusEnum;
import com.vironit.mwallet.services.mapper.UserMapper;
import com.vironit.mwallet.services.mapper.WalletMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Service
@Transactional(value = "hibernateTransactionManager")
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletDao walletDao;

    @Autowired
    private WalletMapper walletMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CurrencyService currencyService;

    public WalletDto findById(int id) {
        Wallet wallet = walletDao.findById(id);
        WalletDto walletDto = walletMapper.toDto(wallet);
        return walletDto;
    }

    public List<WalletDto> findAllByUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        return walletDao.findAllByUser(user).stream()
                .map(wallet -> walletMapper.toDto(wallet))
                .collect(Collectors.toList());
    }

    public List<WalletDto> findAll() {
        return walletDao.findAll().stream()
                .map(wallet -> walletMapper.toDto(wallet))
                .collect(Collectors.toList());
    }

    public void save(WalletDto wallet) throws WalletServiceException {
        if (wallet.getBalance() != 0) {
            throw new WalletServiceException("balance not 0.");
        }
        walletDao.save(walletMapper.toEntity(wallet));
    }

    public void delete(WalletDto walletDto) {
        walletDao.delete(walletMapper.toEntity(walletDto));
    }

    public void update(WalletDto walletDto) {
        CurrencyDto currencyDtoOld = findById(walletDto.getId()).getCurrency();
        if (!currencyDtoOld.equals(walletDto.getCurrency())) {
            recalculateBalanceAfterCurrencyChange(walletDto, currencyDtoOld);
        }
        walletDao.update(walletMapper.toEntity(walletDto));
    }

    public void addBalance(WalletDto walletDto, double value)
            throws WalletServiceException {
        if (walletDto.getWalletStatus().equals(WalletStatusEnum.BLOCKED)) {
            throw new WalletStatusException("Operation is not permitted because wallet is blocked");
        }
        if (value > 0) {
            walletDto.setBalance(walletDto.getBalance() + value);
            walletDao.update(walletMapper.toEntity(walletDto));
        } else {
            throw new WalletServiceException("Value <= 0");
        }
    }

    public void reduceBalance(WalletDto walletDto, double value)
            throws WalletServiceException {
        if (walletDto.getWalletStatus().equals(WalletStatusEnum.BLOCKED)) {
            throw new WalletStatusException("Operation is not permitted because wallet is blocked");
        }
        if (value <= 0) {
            throw new WalletServiceException("Value <= 0");
        }
        if (value < walletDto.getBalance()) {
            walletDto.setBalance(walletDto.getBalance() - value);
            walletDao.update(walletMapper.toEntity(walletDto));
        } else {
            throw new WalletServiceException("Wallet has not enough balance");
        }
    }

    public void transferMoney(WalletDto fromWalletDto, WalletDto toWalletDto, double value)
            throws WalletServiceException {
        if (fromWalletDto.getWalletStatus().equals(WalletStatusEnum.BLOCKED)) {
            throw new WalletStatusException("Operation is not permitted because wallet " + fromWalletDto.getId() + " is blocked");
        }
        if (toWalletDto.getWalletStatus().equals(WalletStatusEnum.BLOCKED)) {
            throw new WalletStatusException("Operation is not permitted because wallet " + toWalletDto.getId() + " is blocked");
        }
        if (value <= 0) {
            throw new WalletServiceException("Value <= 0");
        }
        // TODO refactor
        if (value < fromWalletDto.getBalance()) {
            fromWalletDto.setBalance(fromWalletDto.getBalance() - value);
            walletDao.update(walletMapper.toEntity(fromWalletDto));

            double targetValue = fromWalletDto.getCurrency().getRate() * value; // to BYN
            targetValue = targetValue / toWalletDto.getCurrency().getRate(); // to target Currency

            targetValue = new BigDecimal(targetValue).setScale(3, RoundingMode.DOWN).doubleValue();
            toWalletDto.setBalance(toWalletDto.getBalance() + targetValue);

            walletDao.update(walletMapper.toEntity(toWalletDto));
        } else {
            throw new WalletServiceException("Wallet has not enough balance");
        }
    }

    //TODO refactor
    private void recalculateBalanceAfterCurrencyChange(
            WalletDto walletDto, CurrencyDto currencyDtoOld) {
        double balance = walletDto.getBalance();
        double targetValue = currencyDtoOld.getRate() * balance; // to BYN
        targetValue = targetValue / walletDto.getCurrency().getRate(); // to new Currency
        targetValue = new BigDecimal(targetValue)
                .setScale(3, RoundingMode.DOWN)
                .doubleValue();
        walletDto.setBalance(targetValue);
    }
}
