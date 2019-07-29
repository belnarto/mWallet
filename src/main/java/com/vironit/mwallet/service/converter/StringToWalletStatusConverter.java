package com.vironit.mwallet.service.converter;

import com.vironit.mwallet.model.attribute.WalletStatusEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToWalletStatusConverter implements Converter<String, WalletStatusEnum> {

    public WalletStatusEnum convert(String source) {
        return WalletStatusEnum.valueOf(source);
    }
}
