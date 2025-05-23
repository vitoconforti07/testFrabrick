public class UtilTest {


    public static String jsonStringListaMovimenti = "[\n" +
            "    {\n" +
            "      \"transactionId\": \"1331714087\",\n" +
            "      \"operationId\": \"00000000273015\",\n" +
            "      \"accountingDate\": \"2019-04-01\",\n" +
            "      \"valueDate\": \"2019-04-01\",\n" +
            "      \"type\": {\n" +
            "        \"enumeration\": \"GBS_TRANSACTION_TYPE\",\n" +
            "        \"value\": \"GBS_TRANSACTION_TYPE_0023\"\n" +
            "      },\n" +
            "      \"amount\": -800,\n" +
            "      \"currency\": \"EUR\",\n" +
            "      \"description\": \"BA JOHN DOE PAYMENT INVOICE 75/2017\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"transactionId\": \"1331714088\",\n" +
            "      \"operationId\": \"00000000273015\",\n" +
            "      \"accountingDate\": \"2019-04-01\",\n" +
            "      \"valueDate\": \"2019-04-01\",\n" +
            "      \"type\": {\n" +
            "        \"enumeration\": \"GBS_TRANSACTION_TYPE\",\n" +
            "        \"value\": \"GBS_TRANSACTION_TYPE_0015\"\n" +
            "      },\n" +
            "      \"amount\": -1,\n" +
            "      \"currency\": \"EUR\",\n" +
            "      \"description\": \"CO MONEY TRANSFER FEES\"\n" +
            "    }\n" +
            "  ]\n";


    public static String jsonStringBonifico = "{\n" +
            "  \"creditor\": {\n" +
            "    \"name\": \"John Doe\",\n" +
            "    \"account\": {\n" +
            "      \"accountCode\": \"IT23A0336844430152923804660\",\n" +
            "      \"bicCode\": \"SELBIT2BXXX\"\n" +
            "    },\n" +
            "    \"address\": {\n" +
            "      \"address\": null,\n" +
            "      \"city\": null,\n" +
            "      \"countryCode\": null\n" +
            "    }\n" +
            "  },\n" +
            "  \"executionDate\": \"2019-04-01\",\n" +
            "  \"uri\": \"REMITTANCE_INFORMATION\",\n" +
            "  \"description\": \"Payment invoice 75/2017\",\n" +
            "  \"amount\": 800,\n" +
            "  \"currency\": \"EUR\",\n" +
            "  \"isUrgent\": false,\n" +
            "  \"isInstant\": false,\n" +
            "  \"feeType\": \"SHA\",\n" +
            "  \"feeAccountId\": \"45685475\",\n" +
            "  \"taxRelief\": {\n" +
            "    \"taxReliefId\": \"L449\",\n" +
            "    \"isCondoUpgrade\": false,\n" +
            "    \"creditorFiscalCode\": \"56258745832\",\n" +
            "    \"beneficiaryType\": \"NATURAL_PERSON\",\n" +
            "    \"naturalPersonBeneficiary\": {\n" +
            "      \"fiscalCode1\": \"MRLFNC81L04A859L\",\n" +
            "      \"fiscalCode2\": null,\n" +
            "      \"fiscalCode3\": null,\n" +
            "      \"fiscalCode4\": null,\n" +
            "      \"fiscalCode5\": null\n" +
            "    },\n" +
            "    \"legalPersonBeneficiary\": {\n" +
            "      \"fiscalCode\": null,\n" +
            "      \"legalRepresentativeFiscalCode\": null\n" +
            "    }\n" +
            "  }\n" +
            "}";

    public static String errorBonifico = "{\n" +
            "            \"code\": \"API000\",\n" +
            "            \"description\": \"it.sella.pagamenti.servizibonifico.exception.ServiziInvioBonificoSubsystemException: it.sella.pagamenti.sottosistemi.SottosistemiException: Errore tecnico CONTO 45685475:Conto 45685475 non esiste\",\n" +
            "            \"params\": \"\"\n" +
            "        }";
}
