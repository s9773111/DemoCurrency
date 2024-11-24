package com.test.api.democurrency.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "currencyname")
public class CurrencyName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "currency_code", nullable = false, length = 10)
    @Getter
    @Setter
    private String currencyCode;

    @Column(name = "currency_name", nullable = false, length = 10)
    @Getter
    @Setter
    private String currencyName;

    @Setter
    private LocalDateTime createTime;

    @Setter
    private LocalDateTime updateTime;

    @Override
    public String toString() {
        return "CurrencyName{" +
                "id=" + id +
                ", currencyCode='" + currencyCode + '\'' +
                ", currencyName='" + currencyName + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
