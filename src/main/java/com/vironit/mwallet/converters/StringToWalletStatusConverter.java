package com.vironit.mwallet.converters;

import com.vironit.mwallet.models.Role;
import com.vironit.mwallet.models.WalletStatusEnum;
import com.vironit.mwallet.services.RoleService;
import com.vironit.mwallet.services.WalletService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToWalletStatusConverter implements Converter<String, WalletStatusEnum> {

    public WalletStatusEnum convert(String source) {
        return WalletStatusEnum.valueOf(source);
    }
}
