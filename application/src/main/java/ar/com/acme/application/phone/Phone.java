package ar.com.acme.application.phone;

import ar.com.acme.application.user.User;
import ar.com.acme.application.common.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tab_phone")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Phone extends ar.com.acme.application.common.templates.entity.Entity {
    public static final String FIELD_USER = "User";
    public static final String FIELD_NUMBER = "Number";
    public static final String FIELD_CITYCODE = "City Code";
    public static final String FIELD_COUNTRYCODE = "Country Code";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull(message = Constants.MSJ_REP_ERR_FIELD_EMPTY + FIELD_USER)
    private User user;

    @Column(name = "number", unique = true)
    @NotNull(message =  Constants.MSJ_REP_ERR_FIELD_EMPTY + FIELD_NUMBER)
    @Size(min = 0, max = 999_999_999, message = Constants.MSJ_REP_ERR_FIELD_LONG_NOK + FIELD_NUMBER)
    private Long number;

    @Column(name = "citycode")
    @NotNull(message = Constants.MSJ_REP_ERR_FIELD_EMPTY + FIELD_CITYCODE)
    private Integer citycode;

    @Column(name = "countrycode")
    @NotNull(message = Constants.MSJ_REP_ERR_FIELD_EMPTY + FIELD_COUNTRYCODE)
    private Integer countrycode;
}
