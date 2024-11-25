# DemoCurrency
Bitcoin currency value related applications

## API
* /api/bitcoinPrice/getCoindesk
* /api/bitcoinPrice/create
* /api/bitcoinPrice/quearyAll
* /api/bitcoinPrice/update
* /api/bitcoinPrice/delete
* /api/bitcoinPrice/application

## SQL
CREATE TABLE currencyname (
    id INT AUTO_INCREMENT PRIMARY KEY,
    currency_code VARCHAR(10) NOT NULL,
    currency_name VARCHAR(10) NOT NULL,
    create_time TIMESTAMP,
    update_time TIMESTAMP
);
