package ea.kolokolov.adapter;

import ea.kolokolov.model.TransactionStatus;
import org.jooq.impl.EnumConverter;

/**
 * Converter for {@link TransactionStatus}
 */
class StatusConverter extends EnumConverter<String, TransactionStatus> {

    StatusConverter() {
        super(String.class, TransactionStatus.class);
    }
}
