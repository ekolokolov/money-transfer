package ea.kolokolov.adapter;

import ea.kolokolov.model.TransactionStatus;
import org.jooq.impl.EnumConverter;

class StatusConverter extends EnumConverter<String, TransactionStatus> {

    StatusConverter() {
        super(String.class, TransactionStatus.class);
    }
}
