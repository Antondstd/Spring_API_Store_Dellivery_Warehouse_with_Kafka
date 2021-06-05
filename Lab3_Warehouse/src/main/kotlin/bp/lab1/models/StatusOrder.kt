package bp.lab1.models

enum class StatusOrder {
    CREATED,
    INWORK,
    ACCEPTED,
    AWAITS_PAYMENT,
    PAYED,
    AWAITS_CONFIRMATION_FROM_WAREHOUSE,
    AWAITS_DELIVERY_FROM_WAREHOUSE,
    NOT_AT_WAREHOUSE,
    DELIVERING,
    RECEIVED,
    CANCELLED,
    SPAM
}