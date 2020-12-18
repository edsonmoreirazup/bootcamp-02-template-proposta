package br.com.zup.nossocartao.proposta.associacarteiradigital;

public enum ProvedorCarteiraDigital {
    PAYPAL,
    SAMSUNGPAY;

    public static ProvedorCarteiraDigital toEnum(String name) {
        if (name == null) {
            return null;
        }

        for (ProvedorCarteiraDigital provedor : ProvedorCarteiraDigital.values()) {
            if (name.equalsIgnoreCase(provedor.name())) {
                return provedor;
            }
        }

        return null;
    }

}
