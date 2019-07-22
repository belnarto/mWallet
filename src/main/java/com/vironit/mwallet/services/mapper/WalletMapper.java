package com.vironit.mwallet.services.mapper;

import com.vironit.mwallet.models.dto.WalletDto;
import com.vironit.mwallet.models.entity.Wallet;
import com.vironit.mwallet.services.UserService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Component
public class WalletMapper {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserService userService;

    /**
     * В @PostConstruct мы зададим правила, в которых укажем,
     * какие поля маппер трогать не должен, потому что для них
     * мы определим логику самостоятельно.
     * <p>
     * TypeMap — это и есть правило, в котором мы указываем все
     * нюансы маппинга, а также, задаём конвертер. Мы указали, что
     * для конвертирования из Wallet в WalletDto мы пропускаем setUserId,
     * а при обратной конвертации — setUser. Конвертировать мы всё будем
     * в конвертере toDtoConverter() для UnicornDto и в toEntityConverter()
     * для User. Эти конвертеры мы должны описать в нашем компоненте.
     */
    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Wallet.class, WalletDto.class)
                .addMappings(m -> m.skip(WalletDto::setUserId))
                .setPostConverter(toDtoConverter());
        mapper.createTypeMap(WalletDto.class, Wallet.class)
                .addMappings(m -> m.skip(Wallet::setUser))
                .setPostConverter(toEntityConverter());
    }

    private Converter<Wallet, WalletDto> toDtoConverter() {
        return context -> {
            Wallet source = context.getSource();
            WalletDto destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private Converter<WalletDto, Wallet> toEntityConverter() {
        return context -> {
            WalletDto source = context.getSource();
            Wallet destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private void mapSpecificFields(WalletDto source, Wallet destination) {
        destination.setUser(userService.findById(source.getUserId()));
    }

    private void mapSpecificFields(Wallet source, WalletDto destination) {
        destination.setUserId(source.getUser().getId());
    }

    public Wallet toEntity(WalletDto walletDto) {
        return Objects.isNull(walletDto) ? null : mapper.map(walletDto, Wallet.class);
    }

    public WalletDto toDto(Wallet walletEntity) {
        return Objects.isNull(walletEntity) ? null : mapper.map(walletEntity, WalletDto.class);
    }

}