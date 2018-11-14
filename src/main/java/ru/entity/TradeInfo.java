package ru.entity;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Data
@ToString
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TradeInfo {
      private String instrumentId;
      private String isin;
      @JsonProperty("cfi_code")
      private String cfiCode1;
      private String instrumentTradingStatus;
      private String description;
      private String currency;
      private String settlementCurrency;
      private String priceType;
      private Long minPriceIncrement;
      private Long faceValue;
      private String marketCode;
      private String maturityDate;
      private Long lot;
      private String listingLevel;
      private String dividendNet;
      private String placedIssueSize;
      private String issueSize;
      private String accruedInterest;
}
