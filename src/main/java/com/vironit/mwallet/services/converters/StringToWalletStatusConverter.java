package com.vironit.mwallet.services.converters;

import com.vironit.mwallet.models.attributes.WalletStatusEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToWalletStatusConverter implements Converter<String, WalletStatusEnum> {

    public WalletStatusEnum convert(String source) {
        return WalletStatusEnum.valueOf(source);
    }
}
