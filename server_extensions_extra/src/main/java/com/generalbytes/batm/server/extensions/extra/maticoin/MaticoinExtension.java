package com.generalbytes.batm.server.extensions.extra.maticoin;

import java.util.*;
import java.math.BigDecimal;
import java.math.BigInteger;

import com.generalbytes.batm.common.currencies.CryptoCurrency;
import com.generalbytes.batm.common.currencies.FiatCurrency;
import com.generalbytes.batm.server.extensions.AbstractExtension;
import com.generalbytes.batm.server.extensions.ExtensionsUtil;
import com.generalbytes.batm.server.extensions.FixPriceRateSource;
import com.generalbytes.batm.server.extensions.ICryptoAddressValidator;
import com.generalbytes.batm.server.extensions.IRateSource;
import com.generalbytes.batm.server.extensions.IWallet;
import com.generalbytes.batm.server.extensions.extra.ethereum.InfuraWallet;
import com.generalbytes.batm.server.extensions.extra.ethereum.erc20.ERC20Wallet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MaticoinExtension extends AbstractExtension {

    private static final Logger log = LoggerFactory.getLogger(MaticoinExtension.class);

    @Override
    public String getName() {

        return "BATM Maticoin extension";
    
    }

    @Override
    public Set<String> getSupportedCryptoCurrencies() {

        Set<String> result = new HashSet<String>();
        result.add(CryptoCurrency.Maticoin.getCode());
        return result;
    
    }

    @Override
    public IWallet createWallet(String walletLogin, String tunnelPassword) {

        if (walletLogin != null && !walletLogin.trim().isEmpty()) {
            try {
                StringTokenizer st = new StringTokenizer(walletLogin, ":");
                String walletType = st.nextToken();

                if ("polygon".equalsIgnoreCase(walletType)) {
                    String projectId = st.nextToken();
                    String passwordOrMnemonic = st.nextToken();

                    if (projectId != null && passwordOrMnemonic != null) {
                        return new InfuraWallet(projectId, passwordOrMnemonic);
                    }
                } else if (walletType.startsWith("polygonERC20_")) {
                    StringTokenizer wt = new StringTokenizer(walletType, "_");
                }
            }
        }

    }
    
}
