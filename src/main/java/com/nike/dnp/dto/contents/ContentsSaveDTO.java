package com.nike.dnp.dto.contents;

import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.user.UserContentsSaveDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


/**
 * The Class Contents save dto.
 *
 * @author [이소정]
 * @since 2020. 6. 24. 오후 3:25:23
 * @implNote
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class ContentsSaveDTO {

    /**
     * 최고 메뉴 공통코드
     * @author [이소정]
     */
    @ApiModelProperty(name = "topMenuCode", value = "최고 메뉴 공통코드 (ASSET/TOOLKIT/FOUNDATION)", hidden = true)
    private String topMenuCode;

    /**
     * 2depth 메뉴 코드
     * @author [이소정]
     */
    @ApiModelProperty(name = "menuCode", value = "2depth 메뉴 코드", hidden = true)
    private String menuCode;

    /**
     * 이미지 문자열
     *
     * @author [이소정]
     */
    @ApiModelProperty(name = "imageBase64", value = "이미지 base64 문자", example = "data:image/jpeg;base64," + "/9j/4AAQSkZJRgABAQEAYABgAAD" +
            "/2wBDAAMCAgMCAgMDAwMEAwMEBQgFBQQEBQoHBwYIDAoMDAsKCwsNDhIQDQ4RDgsLEBYQERMUFRUVDA8XGBYUGBIUFRT/2wBDAQMEBAUEBQkFBQkUDQsNFBQUFBQUFBQUFBQUFBQUF" +
            "BQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBT/wAARCAD0AacDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhB" +
            "yJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19" +
            "jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGR" +
            "omJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAI" +
            "RAxEAPwDraKKK/fT8UCiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACii" +
            "igAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKBhRRRQAUUUUAFFN8cW+q/D/RYNT1XQ9QgtZplhWSWBo1LFWbhmGDwprlvBfiibxNqGrOSy2sfleTEwXKZDbuQOckV" +
            "nGpCp8Dub/V6ii5tWR1dFFFaHOFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUVet9D1G7jWWDT7qeJukkcDMp/EColOMPidioxcnaKuUaK7" +
            "Lxt8LNY8B/DeTxpqUlsdMjigle3iLfaAJWRVBVlAyC4zz2NeOQ/FLT7hN8Wm6nIv8AeSAEfo1YUsVRrpypSulodMsLWh8cbHY0VV0vUE1WwhukjkhWQEiOZdrjnHIq1XUctrBRRRQIK53WvHmlaLdXFlJcf8TCMDEGx+S" +
            "QCBnGOhroq8H+JRls/HN/P5bAZRkZlO1sRr0PevjuKM0xGV4JVcMtW7enmfo3A2R4PPcy9hjW+WKvZPfXY7z/AIWVN/z4x/8Afw/4V0PhvxPFrylCoiuQCxjGThQQM5x7141FfvJdWkYKkSRl2x1Bx+ldN4d12Pw/d3Fw" +
            "4JZoGSPpjfkEZ5HHFfk+T8X5hTxsFjqt6b3uv8j+h+JPDnJq2VVJZTh+Wsvhs3q+zv0PWqK87sviVcfa4jdG3+zZ+dYl+bp2y30r0C3nW6t4pkzskRXGeuCMiv2nKc9wedKTwrd47pqzP5k4h4TzLhmUFjkrT2ad16Ele" +
            "zfs6/Dew8Xahc6tqJS4gsJBGbGaESRzbkbk59Dg/hXjNeufs7+OJ/D/AIsi0Z2totM1F2a4ln4K7InK4bIA5x2rfOFVeCm6Lt/keBl3s1ioe1Wh9CfE/wAIaR4s8C6nY6rp1rfW8FtJLAlzCsghkEThZFB4DAMcH3r4J8" +
            "M+G7bw1py28IV5f+Wk+wK0nJIz9M4r9HL60j1LT7i1kJ8q4iaNmU87WGDiviP4n+GLPwd451TR7EytbWpjCGYhnOY1Y5IA7t6V8vw1WSnOlLd6o+jz+EuSEo7HK0UUV+gnxIUUUUAFFFFABRRRQAUUtcf4i+JFho8t1aR" +
            "JJNdon7uRArRbiuRk7s4yefoaDWFOVR2irnSz6nb29/a2ckm24uAxjXBOQoyecY6etWq8c8FanNq3xBs7q4P7yRpXIXO0Ext0z0r2OhO5pXpexaiFFFFBzBRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFF" +
            "ABRRRQMKX1Hcdfau68C/CPWfGJ068SFV0ie4WOWdZkDqm/a5Cnkkc9q9d8K/CG+1T/hK/DniTw5pWlaHayQRaFrtgI21C7iDMzyTNztbhB0XO5q+bxWfYXD1PZRfM07O3Q9nDZTiMTBytZW0v1OT+Avwh0rxpYXGtaxi7" +
            "tEke1FiQy/OAjCTerA9CRjFfR2g6Bp/hjSYNN0y3+y2MOfLh3s23LFjyxJ6k1k/D3wDZfDnRZtMsbie5hknactcFd24qoxwBx8orp6+BzHGzxleUuZuN9PQ+zwGEjhqUfd97qeMftXyWGq/CLWPDkmoRWuo6gIHgib5nd" +
            "UuI2JA7j5T+VfKOl6Ta6JZi2s4/Kh3Ftu4nk9+Sa928ffB3x94q8ValeNE17aG5lNobi9U7Ii5KhQzfKMEcdq5XUPgP4y0uxuLy506JLa3jaWRhdRnCqMk4z6A19plFTB4OgouqnKWp8tmX1rE1G/ZtRR57RS0lfXHzgU" +
            "UUUxDLiaO1t5Z5nEcMSl3duiqBkk188ePPjZD4k1VrS38LHU/DMX+o8WR3zC3jVgN8gQJyFbKkE/wmtP9pv4neH9Bsbbw7eeINR0fVvlvxHp6yL58WJEEcjKMFGYcrnsK+QtO1jxZ8WvF1n4Y8OE2P9sTpZW2j2c5gtNz" +
            "4UKFLbVBOSc8ZJr8z4mzWcm8FSS5ftN/l6o/VeFcuo4ZRzOvJ81/djF2enV+TPVPE3xRhsS8fh+8juJf4b2FlZV5wRtIIOR/Osax+LmtC4b+1Jf7Tt9p2x7Ujw3Y5C+ma4m68HXngXVdS0PUk8rUtPupLS7iDhwk0bFHA" +
            "I4IDA8imV+VrC0FGyV/M/XZZ5j6lT2vO4+S2PafCXjBfFX2r/RPsph2/wDLTdnOfYen619P/DafVJvDa/2qGDqwEG9Av7nYu3GByOvNfCvgvxRdeEdcg1CCOK6SIkvaXOTBLlWA3qCM4zmvoLwH+1S91rCW3iS1sdM0oR" +
            "EJJZQylg4xtXGTgde1fXcMPC4DFurVqNX0S6fNnyXGGPxWbZdHDKkpOLu5dV5JefU+nNLtYb7UrW3uLlbOCWVUkuGGRGpOCxGRwK+lPB/gH4TatDp2mpLZavrDQhXaO5lVpnC5dgofjoTgdK+YatafqN3pd2l1ZXU1ldR" +
            "52zW8hRxkYOCOehP51+rZhgamMXNTquNlstn+p+HYPFQw8vepp3fXofoDFGsMaRoMIgwo9BXnXjT4G6L408U2etygQsJN19DhmF4AqqoyHGwgL2HOa9A0+QyafbMW3N5S5YnJPFeJftPeJNW8Pr4c/svU7zTvO+0eZ9ln" +
            "aPfjy8Z2kZxk/nX5pgYVp4lU6M+WTurn3+NnSWGdSpHmirOxg/HP4J6R4V0Nte0YjT7SAJE9lh38x2fG7ezHHBHGO1eCVsal4x17WbVrbUNa1C+tmIJhubl5EyOhwTWPX6nl+Hr4aj7OvPmdz87xlalXqc9KPKgooor0" +
            "zgCiiloGGKhuryGzt3nnlWKFOWdjwK5nxt44Hhorb2oSS+yrNHMrYCEHkEY5yBXmGs+KtS1m4nkluZY4pjk28creWOnAGanmT2O6jhJ1PeeiOr8QfFWeVZ7bTofsrrIVW6WQOGUHH3SvcV53tA6euaWioZ7lOnGkrROk" +
            "+HH/ACOmn/8AbT/0W1e3V5v8N/Bs1vcxaxdZTau63CsCGDKynd3HWvSK0jseHjJxlU917BRRRTOAKKKKACiiigAooooAKKKKACiiigAor1H4afAm/wDH2nPqFxef2TYsB9ml8pZvPwzK3AcEYK9+ua7T/hk9f+hn/wDJ" +
            "D/7ZXi1c4wVGbpynqvI9WnleKqxU4w0Z89UV9C/8Mnr/ANDRj/tw/wDtlfLXxO8Wf8K78eaz4bFr/aA06fyftPm+X5nyg524OOvTJrXD5phcVNwpSu/QVXLcTRV5xOgorjNL8carrdu09l4fM8SsULC7UcgA45X3Fb+i" +
            "6jqV7JKL/SjpyKMqxnWTcfTjpXpKSlscMqMobmpRRRVmIUUUUAdt8INDOteO9JP9oWdh9kuYbnbdzeWZtsi/JGP4mPYV9q1+fVneTafeQXVtIYbiF1kjkXqrAggj3BAr1bwT+0VrXhtLtdVWXxB5xTyzNceX5WM5x8hzn" +
            "I/Kvi86ymviqntqNn5bf8OfUZXmNHDQdOrpfqfVV1OLW3kmYErGpcgdcAZr518dftNy3sNp/wAIslxYyKzeeb2CNgwwMY5PPWvMviv+1P458YeH207QvDE/haVLkSNqEGqJKZYlDAoVKDAbIPX+GvPvDmtf8JBo8F95P" +
            "kebu/d7t2MMV64HpXPlWRpNzxkdei/zOvMsylypYaWnU+kPBP7TrWdjdL4ojuL65L5gaygjUKuOhyw715p4x+LniPx1p6WOq3MMlrHMJkWOFUIYAgcjrwxrjKK+lo5ThKNV1Yw1/L0PnamYYmrD2cpaBRRRXrnnBRRRQ" +
            "B88/tA/sq+J/jn4yl1/Q9S0mztdM0UtNHfyyrI3lO7tt2xsOQ4xkjvXt37Avh3wZ8E/2U774wz6beT38tjPfat9nk3tKlrc3CoI0ZgqnaPUZPNdn4U1qDRb25N1bLd2t3btayxu2BsYjdng9ga9o+Hvg3wXH8P/APhDt" +
            "M0O0fwVfxSWh01VZYTG7sZF2nnBZm796/C+MaVTC4vmcX7Odnf80fsXDFaGJwnIpL2kb6eXc29Y8M+Ev2m/gvp66vp903hrxLY2mqLb7/IuAjBJ4wzITgg7cgEjgjJFfBnjD9mP4afFb9mO/wDih8HtM1Dw8dIku7q9j" +
            "8R3rs8lpawytKiIhkXzCwTaSRwDkiv0W8N6v4Z0mOy8K6O0VrFp8QsbaxiRgsSRLtCDI6Kq469q+Pv+Ci0S/s8/sn6fo3w4QeEtJ1TXv7PvbXThtSe3uLW5M0bZzw+0Z+lfFYe7n7NXTb09D6ur7SkuaXY/M3S7yK+h8" +
            "2JwQwGVyCy9eoHSvfvgj8A5vFzf2t4itJINFwyRwSGSGaRtqMki/KAUw3XPNeU6f+zL4xvvAvhjxD4RMmsnWYXmurdfLg+y7W2oNzyDfn5jwBjFfeXg2xudM8IaFZ3ibLu3sIIplJB2usahhkcHkHpX6nkuRe0xDqYmL" +
            "srNdmfB5znbjh1Cg9XozZpKKK/VttD8vPWfgj8Ym8DXX9makzPo07EqI0XMczMg3sxIwoAOa4v9s74kp4w8YaBYeG7K9upfDzzM9/HCJbWbzUhZfLZSd2MEHOOR3rmqXJrxXlND60sVHR/qexTzOrToewlqjK8N6pPq2" +
            "mJJc2txa3CAJILiLy9zYGSo9MmtSl3H1pK9o8mTUndIKKK2vCfhDVPGmrLp2k2/2m42+YyhlXamQC3JAONwrOpVhRi51HZIIQlUlywV2VNJ0HU9eeRNM0671F4wC62sLSlQemQoOK908G/AzR9H8Lm/8aRmW5uFjmt7S" +
            "3meOdEYDKsp2/MCTnrjFdpoejaX8H9HbStP8u71qRGS5v1jMT8kshI5BwGx17Vj6hql3qro95O87IMKX7CvhsVmVfHe7S92F9+rR9nhcsp4e062srbdEc3+0DoWkfFDw/fb7OeRLW3M9tEp2yeckbhfunn73TvX5832n" +
            "3el3ctrfW01ndxnDwXCFHXIBGVIyODX6Q/Wvhn9oA5+MHiP/rpH/wCikr1Mom4p0VsdOJin7x57XReA9Fm1jxBA0LRqLR0uH8wnlQw4HHWsfTdLutYuha2cJmnYEhcgdByeSK9t8L+E7Tw1a7Y9slyQQ9xt2s43Zx1PT" +
            "+lfTI8LFV1Tjyrdm433uKSiirPnQooooAKKKKACiiigAooooAKkjhkmyI42kI67VJqOvTPgX4wPg3xBeXUli1xYPCovLzfsisYQ2WnkbBARRkkkjoea4sZiJYahKrFXa6HThqUa9VU27XPO/sNz/wA+8v8A37P+FWPD6" +
            "LJr2mo6hka6iDKw4I3jIr7t03UrPWtPtr/T7qC+sbmNZYLm3cSRyoRkMrA4II7ivP1+FPw4t9Q+1pbWq3Sy+aH/ALQk4cHOcb8da+SjxEqsJRnT3XTU+mlkUqcouE7+p6Ha2sNjbpDbwxwQqMLHGgVR34AqauV8cePLf" +
            "wr4R1XVrCFdevbOBpYdMtZh5tyw6IuAxyfZT9K8GT9sTxY0YZvgtrinGSDcScf+S1fJ08LXrXcEfWOrCnoz274u+LrXwj4JvnnadJb2KSztntxkrK0bFTnIx0618U3lpBqVxJcXcEdzcyNueaZQ7sfcnrX07LeN+0J8N" +
            "rCXVYj4DvUvWkaxviZZAqhlBw3lnDbs5x2rzHxp8FL/AMPyWi6JcS+KhLu846faFvIxjbu2s3XJ64+7X12SVMPhYuFV2m31/wA9j5LN6eIrzU4L3EjzG3t4rWPZDFHEmc7UUKM/hUldJ/wrbxZ/0LOr/wDgFJ/hUVz8P" +
            "/E1nbyzz+HtTggiQyPLLaSKqKOSSSOABn8q+uWKw72qL7z5l0K27i/uMCiiiuo5wooooAKKKKAD1BAIPHIzTY40hXZHGkSdlRQoHOegp1FAwooooEFFFFABRRRTAK47wH+0t4l8I/tJab4FjttPl0K71K0tXeaORpkR1" +
            "DMU/eBQcuf4ewrqtQu/sFhc3OzzPJieXbnGdoJxn8K+bvBfiCDxhqvxD+JEVibDXdFt7e/0tvNMn2WZEYBsEBX5jBwyke1fH8TypvAunNXv/S/E+v4Zo1KmNTg7W/q33H6bfEP4u+D/AIV6BdeJNUtnEcDLu+zQIZ2Lu" +
            "FBALAnlhnmvzc1b4za38ZPjNaa74jS1vINQ+z6e2jSb306Ab1Xzo4XdgsuMjfk8E8c1418Rvix4p+LWp2uoeKtU/tS8tofIikFvFDhMlsYjVQeSetQeH22aevcbzkV8Pk+UrBx56nvVO/RH2WYY/wCty5I35O3Vn3bb2" +
            "MGmwJbWsMMFvGNqRwKFjUegA4H4VJXh3wO+Jl7NqFl4Wvv9JtZf3dpJlU+yqFd2yAMvu46njFdvoXxb066h1eTX/s/hZbHUZbGM6hdBRcIuNsqlgvDZOBz061+kYDMVVXsqy5Zr7n+nyPgcyyt0Je2w75oP715d/md1S" +
            "M6RoWdgi+rHArkb74reG/7E1W80jWNO167srZ51sbO8R5JmCkqg25PzHgHB+lZfw9+G3jn9oLw5qdxrOpXfhHR7l45LfT73Sg/mRt+8ULIfLLBcKN3fvinmWdYPLKXta89DHLsmxeZVPZ0Ynffbbf8A57xf9/Fp8c0c2" +
            "fLkV8ddrA10037JPw3k02WGPRDFetCUS6N5cHa+0gPt8zB55x0r418X+ItU/Zp+KWp+HNMZvEsNrJD9vjgjERlUxLIoI2uU/wBa3IPb8K+cyvjLB5pUdOCat3Posz4QxWW01OUk7n1NRXmnw1k8TeJLyDxHc+M4b7RrlD" +
            "K3h+OxiBs2ddwhadcMTGWAOQCdvQZr0yvu6NT20OZKx8NVp+yly3ua/hPwnqHjTXINJ01UNzPuCtKSEGFLHJAOOFNfV95eL4BWwsrHTtNjuRYxrNPHAA5bGG+YY44zXD/CnSrj4c+DU1S2uxcT+Ioo5U/dhfs3lk5HJO/" +
            "If2xjvVmSRppHkc5djuJ9zXwmYV3jq/8AcjovM+zy3DLDUed/FL8AkkeaRnkdpHbqzkk/mabRRWG2iPSCvmP9r6ztIrjw29tDCt1M1z5zxoBI+BFjcRycDpmvpqSRYo2d2CooyzHoB618s+JNIt9S+L/ifVmiM0XmwvZ3" +
            "IY7G/dAMVIOG6AV7GWU+etfscGMrKjTuzF+G2gtpGgLJcJCZp386N1GWEbIuATjI+ldZRRX2B8PObqS5mFFFFBmFFFFABRRRQAUUUUAFFFFACjjmvT7f4z2VrDdW8fgzR1truIwXMKoAlxERhkcbfmUjgg8HNeYs5ZUU4" +
            "wowMKAepPJ79e9NrysZlmGzDleJje3m1+Vrnbh8XWwt3Rdr+h7Zpv7TM+i6fbWGn+F9OsbG2jWKC2t3KRxIBgKqgYAA4AFaWkeMtL8R3TR2lyJLgr5rxhGAXkZ5IHc14DTlZkyVJBxjrXNLJsNFfuVys76ebYhP94+Y+k" +
            "6ydY8VaXoMyQ31z5Eki7lHls2R0zwPavBPPk/vt+ZprMz/AHiWPuazhlCv78tDonnDa9yOp6drHxaW3vmSxtoru3wCJXLKSe4wRTLL4uXUm/FzLo+Mf8esjjzPrj0/rXmVFGOyPC47DPDTuk+q3MsLnWKwtZVlZ26PY9T" +
            "0H9oDXdD1N7iVpdTi2sgiubpynXhsevH61z/jH4r6/wCLtUvrg393YWd0oRrCC6fyQuwKRtzjBwSeO5rjKK2weS4HApeyht31OfFZpi8Y/wB7L7tAooor3DyAooooAKKKKACiiigAooooAKKKKACiij60wPPvjh46n8Ae" +
            "CxeQWkd215P9iKyMVADxuS3Hcbf1r5F8H+LrzwhqCSxM1xZSOpvNPeRlgvEHPlyqOGXk8EHqa9C/af1K11L4g2xs7qG7hSwjVjDIHVXEknBweDivIK+HzSaxNVwlrFH3OVxlhacakHaRoalc/wBva9e3NvZw2SXU8kyWl" +
            "vgRwqxLbF6cDOB9K6IR+TCVijUHHCrwCayNBswczurK6nC9gQR/9etW6Es1vcrBBNcskTPIsCFjGgHLtjoo4ya5KcY043ex6b5qkr9TZ8C+LH8O+ILXU0hWW6syd9uxIXLKRjP0Paun8TeEPDPj7xJHrPivxffeGfh49n" +
            "i61C2tmnEeqElxAIBlmG1v9Ztx0561kfCv4X+I/iLeaZp2n6Zc2y3QITUbuCRbU4DHJkCnj5SO/JFew+O/2c/CXwn+FcurfFOTU9QlfU47eFfDFwpX50+QsJVX+JWzg9CMV4+OzjC4eHsJzvJ7Jbnbh8pxWIbr042S3b2" +
            "PlSNvC/hHwH4U8QaD4tvLzxncXcn9s+Hfs8kMNvCjt5R87G2QOoUkAnG4jtX098F/+ChMdvqHh3w94q0ay0Tw3b2/2e41dJJppIwkTbD5aqc7mCjgcbs1j+KP2QfB/wAL/A/hjS/Fk+o3XxE8TXUmlWFxpNyG01Lx3It2" +
            "l3oriMbo92Ax4bAqfwB/wTb8SQ+LrB/GWraTN4bHmC7j0m7lF0f3bbNm+ELjftzntmvjMbj8vxuHcMQ9Fe3r5H0mCwWZYOsnh1q7X/4J7s9p8YfjqP7H1zT7r4T6LGft1t4g8P6uks9yR8qQsqSBgrrIXOe8ajvXk3xs/" +
            "ZV1rwJpjeJLbWdQ8ZybJJdY1bVHQTqqBRGzMzlpOCV4zgIK+5LW3Sxs4bdD+6hjWNSx52qMAn8qzbwtrLPaIqta9JnYZDqf7h5zXxOCzqrga6qUYpQXS3T8z7vE5NTx1JxrSbl37P8AI/P34OfE618HtJpeohbfTp5Wne" +
            "7wzMrbAAu1QcglR+dfUXhjSV17xJpWmvIYlvLqK3LqMlQ7hcj6Zr52/as+Hul/D/4lQR6WZ9mpwG/mEzhtsjzSAhcAYXgYFfc/wV+EcbeLo73WbhDHYIt5CbeQjEiOpBbK/d61/QOD4jwywSlOfK5r3V1ufgmZZHWjjX" +
            "CEb8r959D0TxBY2Xh/RdE8PWl0102lRvDI7oVOflxn8j09KwK1PFUiTeJNQdGDo0pIZTkH8ay646KtBN9dT0JaOy2QUUUVsSY3izVLbS9EumuZfKEqNCnBOXKnA4HtXgQ9e9eqfFW8e5s0sIrS6kaORZ2lWLMeNrDr+Pp" +
            "XllfWZTGCpNp69T5HNpylVSa0QlFS/ZpfJabyn8pTtMm07QfTPrUVe0pKXwu54jTW4UUUVRIUUUUAFFFFABRRRQAUUUUAFFFFABRRUtpayXt1DbxYMkzrGuTgZJwP50m1FXY0m3ZEVFWf2lNF1H4D+HPBk4SOTVtXa7W8" +
            "hnbfEnllNmzbg5IfnJNcx4L1PVdY0hbzVoLe3aYB4Rb5wyFQQTknnmuXDYqnio89J3R11sLUw/8AEVjeooorrOMKu6bo95rDutnbvcMgywXHA9apUeZdx/8AHrqF3YH+JrWUxlh6HHWuHGSxKoyeESc+l9jqw/spVUq7a" +
            "j1tubf/AAhOuf8AQNm/T/Gl/wCEJ1z/AKBs36f41h/atV/6GHWf/A1qUXWq/wDQxa1/4GtXyPtuJ/8An1T+9/5n0Pssk/nn9yL2paHf6P5f2y1e38zOzdjnH/6xVD8a0YfHOu2lnDZyWNhrEcGdlxqQaWY5OTklvw+gFH" +
            "/Ceat/0LWgf9+D/wDFVjSz7N6a5MRgXKS6xenyKqZXl82pUcUkuzWpnfjRx61o/wDCeat/0LWgf9+D/wDFVb0vx+6X0TazoOjW2mDd50tvbFpF44wMnvj8zV1OJMdTi5ywE0kTHJsLOSjHFRuzDoral134fySu/wDaGpJ" +
            "uYttWHgZPQfL0qxpdx4F1jUbayt9Q1Np7iQRoGiAGScDnbVrjLLlHmlGa/wC3WT/q5jL2i4v/ALeRztFdfdfDfUxdTC2RGtw7CNpJRuK54J49Ki/4VvrX/POH/v8ACvXjxLlEoqX1mOvmcEslzCLa9izlaK1tb8M33h9Y" +
            "jdooEuduxg3TGf5isrHBJ4A9eK9ihjsNiYKrRqKUX1TPPq4WvRn7OpBp+glKM9uvajafQ/lW94f+Dt58XtPvbCO9vdKslZFnvdNuBDcxn7y7GIPUrg8dCayxmYYfB0ZVqslZGuFwdbFVY0oRd2fN2h/sv2nx2+IV2fCdy" +
            "NF8KwWzLNq0cDXURv1kG6AhnUhisit1xgD1r6SP7B/wx8H/AAG1CLxO9vB4htbK4efxhOs6iD53ZJjbrNtPlqVGO+33r6H8GeAtA+FPhe4MVha6fZWsbTzrbW6IJSqAtK4QDdIQnJ6nFfnF+11+2Nqvxk1+bRPC2pT2Pg" +
            "iBSkTWrz20moJLFF5iXSb9rqrhwBtHBPrX4ZHG47O8W5UnyU076H7B9VwuWYaMJe9O27PPfij4Z8H/AA/1HT7Hwz4+s/HNpJbB5Ly1tWt1icEjYVLNk4AP41zXwz1y4b4iaXZxN5dnrU8OlX8JAYT2ssiLJGSRkBh3GD7" +
            "1x1pps98rNGAVBwckCun0yzXSZIZrclLmGQSxzrxIjAghlYcggjINfcSpTqUHSk7u2/6ngUqyo1o1UrJM/TXwLcaZ4N0LTPD9hZLZaXZp5UeZWbYuSf4sk8nua0vGngfwt8XvD40nX7KPWdKSdZ/J814wJFztOUIPGT37" +
            "14b8CPHdt4w8G29oby4vdV0+JReyXO5mLMzlTuJ+bhf0r1fS9Wn0yUeUdynjy2J25PfFfz5jMPWwuIlGT95Pc/orDwoY3Cxq0Vo1t0Oj8V/Dzw/43vtDvNb05b+40S7W+092kdfInXG1wFYZIwOuR7VvXF1FaxtLM4RB1" +
            "Y9qx1utdbBFta7fqf8AGp4dHaaQTXU8su75mt2bdFk9sHsK86TbspPRCVKMG2Y/h211ZrWS0udZOqq0rSG/ECxjYcDydoPb1966i3t47SBYoUCRr0X05zTobeO3QLCixLnO1BtH1r5l/ag/aPm8L3Fx4R8NTeXqJR4tQu" +
            "MSRy22UjaNoZFYDOGOevTFd+BwFfMqyo0Eedj8wo4Ci6lR/LueXftkeJdL8SfE+xGm3sd4bGy+x3Ijz+6mSaTch9xX6R+FNPg0vwtfatPeKn2y2mtY4iv8XbnPfHpX4z3FzcahfSXFzNJc3M8plkllYs7uTksSTkknnJr9t" +
            "viNbQWvhqwighjgi+0KRHGgVc7WOcCv12eU06U8LScr+zv95+PVMwniHWq2tzs84opKWvrjwwpKWkNRN8sWxxV2kVLmNLpHSRco6lGXPUHrXL6j4J0a1s5JYNH+1zKuVhWVwXOemc/WuprP1bR/7WWMfbbuz2Z+a1l2E5x1" +
            "4r+e6eaYqnjJT9q4py1s2vyP0argqNSgo+zUmlpdL9TyfxNe3Fmi6aunto9nIqzNaM/mb2yfnyeewGPaudrv/FHhO3sr9bm/v72bT1iCtPLIJJQxJwAMdK4D1r+pOHMZh8Xg4yw2ul29dX89bn4VnGGrUMS1W07LTT7tBK" +
            "KKK+qPCCiiigAooooAKKKKACiiigAooopgFex/Av4Vz6p4vL+ItDv4bGK2+0QSzRSQp5odNuG4zxniuA+HtnPqXi6xtbfSI9fkfzMabNKsazfI38TcDGN3/Aa+4luE0/SVmnAt4oIQ8iDnywF56dcY7elfG55mVSg/qsI/" +
            "Et76/cfS5TgYV37ab0T2Pmv9sC10fXtU8Iq80VzqOkyXD+VHN88DOIiNyg8ZABGa8ONfSUniP4T6h4u1vXdU1eHVf7R8ny7e402bEGxNpIO3ndgdhjFfP/ih7KTxJqr6aFXTmupWtgilR5Rc7MA8gbccV1ZJU5KX1bkasr" +
            "3atv0Rhmqc6ntuZa9EZlFFFfTnghRRRQAUUUUDCiiigAooopWQC0yWNZo2jcZRhg9qdRWcqNOScZRVmXGpOLumZ/8AYFl/zzb/AL7NH/CP2J6xt+DmtCivLeTZd/z4j9yO3+0cX/z9f3lmx8VeJ9FtY7LTb6CGxhG2KN4l" +
            "YgZzySOeSazPF+reKfGGjNp99qFu8HmLJtWILyvI5Aq1RXh1OD8pqOT9nZvsz2sNxNmOFnGpCd3Hul+IzQ/i54t1qE7dSgtbpc+ZZz26CaPngsuMgEYI+or6c8C+JrC30PQp7jxHpNtcS2wbUo5bmNXeTbxgH7uCenFfH/" +
            "i6xj0mC68RW032GazQ3V28a83Uca58tiOgwvoaxrH4zaNfLpUoZfIuozJPKA/+jnbkDG35snjivksZw3leHSoYibpyWz35l+lup9lhcdmGPk8RgIe0T3j1i936p9A/4KVfF7UdW8ZaN4L0/V7W98KLYwaqYrYRyYu/MuI8" +
            "mQc/cI+XOOc4r440vS3vZQzgiIEE7sjcPY19HftDfCfUfGU1v4n0ZjeyQ26QyWYCptiXzHMu5mHqBtxnn8K+ftD1ONo47dvlPRW67iTXt08vhl0VRhrHo+/meT9cljW6k9JdV28jW/d2sIBYJGoAyx4pfOj3Mu9dyjcRkZ" +
            "A9aWWJJ4ykih1PY10HgLwG/jzxRa6dGBDFKyLeXeFYwW5dVeTaSN20NnaDk4ro5X0RDlFL3nZGz8H/AIryfDvXXlSXztKnKm9hgCO8gVW2AE9MFs9RX2F4b8caH4pjtRp+qWc91NAs5s47lHmjBAJDKCTkZwfevFte/YZF" +
            "x4V1S/8AAXjBvEWsWYj26XHpwtPPLOAB5kkoC4Xc3PpjvXn+sfB34o/BDTI/EepaZP4fjytqbyK8gdtzj7uEdjztPbtXxOa5Rhs0m5KXJU89Ln3uS5/iMqgoNc9Lyd7eh9xaH4kbTVMMwZ4QPkVQMg55q9qHxI0rSbV7q" +
            "9kFnax43z3DqiLkgDLE4HJA/GviT4XxfGb4zX97Z+Dr3U9ZubONZp1W9ii2KTgHMjKDz6V6Va/so/tF+LpV0rxHZajbaLcH9/LLqFpMo2/MvyCbJ+YLXyT4TdJ81arGy3tufTVeLcLUTdOjLm6bWuRfFX9sL/hKPD8mje" +
            "ELO+0/UJ5mglmuIopEmhKOrIgBJySVIIGa8W8I/BPxH4ra78yBtI8gJg6lDJF5mc/d+XnGOfqK+y/g/wDsE3OmFrPxPaQxmNjcQ6nLBFI24Fdse1ZSRjkg57V7d/wyhH/0NDf+AI/+OV7NOu8DSdLK6Nr7ye7PicRUqY+" +
            "p7TGTv5I+eP2Of2a7PTfFUd9r2nS6ncI86LqNo0wt4lMQxGx4G4nP/fQ9q+sviTq1zLrD2DPm1h2SIm0cNt65/Guh+GHw7Hw00O709dQOoedObjzPK8vb8qjGNx/u9fevNby8uL+4aa5maeZsZdzk17WVxrVZutiHeR5m" +
            "JcYQUIbENFFFfTnmBUF5cx2tu0ssiRRr1d2AA7dTU9cT8Sda+y2Y03yd32lQ/mbvu4YHGMc9PWvJzWsqGCqScrafidWFjzVoq1zorfU7S8kKQXUEzjkrHIGOPoDU000dvG0krrHGvVnIAH4141o8l8uoRRadI8d3cMIUE" +
            "bBSxYgAZPvivRP+FX/EmT5LnTJri3YfPDJeQ4Yeh+f1/lX4LHL6tV3gm11Pv1joxVpLUoeLbjwzcR/bbx01CaPEYjtrgb8Z9A3bNcJr11odxbRDS7Ka1lDZdpX3AjnjqfavRJPg54rZsf8ACFxMD1b7ZBx7/eq54t/Zh1" +
            "fS7OGTQrs69O0m2SExpb+WuD82Wk55wMD1r984YjhcFRpqtUmn0Tfu/NJ6fM/Lc8dfFTk6cItdbLX72eJUVb1TS7nRdSubC9i8m7t3McseQdrDqMg4NVK/W4yUkpRd0z8/acXZhRRRTJCiiigAooooAKKKKAClFJSjrQB" +
            "9e/BfwVong3wXaa55uJtQt4bmaa8ZAIWK4whwNo+buc9K+dviD8S9a8Ya9Ncz3H2ZUBtxHZyOkbKrNyRuOSc9a81bxx4/8cWmq+E/EOsSP4FhkW3TSpLKJPPhRt0W2VVD/KyIc5ycc9TWnHGsMaog2ooCgegAwP5V8zl+" +
            "WTp154jFe83se9jcZGVKFCjoluPpKKK+nPCCiiikIKKKKACiiigAooooAKKKKACiiigAooooAKKKKAK+pWMeqafdWU2TDcRNC+MZ2sCD19jXkuj/AAjh8G63e3ZmkuIJpS9vEzh1VeRgjaMfer2KmzQpcxmOVd0bdRnGa+" +
            "Yz7JIZxQcU7TWz/Q+84R4mlw5jY1akeak37y/C6PPtL1ZvCLNDcB7vSrhsYI3yiRiBzyBsAH61g+PP2ddE8WTanrOmXU0Gr3CZt4kkjS0VwoUcKhOPl5wfWuz1TSWsyykF4XGNwBA5zxU/gfTbjTYZ4Q+3SgF+yW+zHl8" +
            "sX+bq2T6+tfE8P43EwxH9j4+Ddtn2/wCAfqvHGS5fiMGuJ8mqKMZW5l3v28+6PENB/Zg1241i0HiDUbVNKjVlk/s24bzj8p243x4+9jPtXvPhDwXp3gvTRZ2KvJhmYzzhTKcnOCwA4rdor9RoYSlh3eK1P57rYyrX0kzX" +
            "8LWF9rGsQadYXgspbgkCSSUxxjCk/MR9D+devaR8O/GFvpT2Bk8Oa3ZtP5rfb3kuArYA44xkAcfU+teF9sdq6Tw38RPEPhGwez0nUfsls8hlaPyo2yxAGcspPQD8q+Y4iyvMMwpOOAqRi30kvxvuetlGY0cDVU6yb+en3" +
            "H1s/wAOfDF54I1nQLOJNBj1ixmsJ73TVit7tEkVlLK6rwwzlSQcHFbvgvQbDwP4Q0Xw9balPf2+l2cVnHdahOslxMqKFDyMANznGScDJNfGN78UvE91JJNLqe+QjlvIjHQf7vtVrw78Xr61WZNTRtRdiDGylY9g78Bea+" +
            "AxNB5biaGBxk71Ki3S0ufouAjUzTB18ww8fcpvVPfvc+3/ALZb/wDPxF/32KPtVv8A894v++xXzlp+uafqrbLW7hnlCb2jjkDFRx1x/nmtDFerLLZQdpSs/Q8yOLU1eKuj1jxd40TQ18i1xLduoZWIDRgZOc4Oc8V5PS" +
            "Ypa7KFBUFZbmVSpzu7CiiiukyCqd54T8N+IpFl1yfUYpY/ljWyaMKV753AnNW81n6lb6fcSIbsx7gMKWfH9a8zMownh2ppNebsvvOzCa1UtfluetQiw1LTIrrR7DToZUkx/pkKqQAOo29845rjNfvr9dYuhPcYl3Dd9n" +
            "dvL6D7vNcX5s23A1iHHYbV/Kqd5q2pWzDNz5kR4SUIu1senFfK/wBsYbL4ucqbt5Wf6nuRyutiXyxmr+d1+h2H2+6/5+Zv+/jf40v9oXX/AD8zdf8Ano3+NcpoerXd7qCxyy748EkbQP5CtrUr0abp11dld4giaTbnGc" +
            "KTjP4V9PlmOo5tS9tQjZXtqeNj8LUy6pyVX0ueUfFHSrbT9WjuYZmlnumkkm3OGwwI9uOpriqu6xqA1TVbu8WPyRPI0gTOSuT0zVKv1bD03TpRjLc/LsRONSrKUVZBRRRXQcwUUUUAFFFFABRRRQAUtJRQAtJRRQAUU" +
            "UUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUDGyRJMu2RFcZzhhmljjWNQqKEUdFUYFLRUKnBS57a9zb29V0/YuT5e13a/oFFFFWYBRRRQAdeCMimxwqpJC5JPp0p1e1/s9fD/U4/E9p4qvETT9JsS" +
            "25rwNGZhJEyq0ZK4YZI5zXh5nHCUorF14Jyh8N+57WX1sXLmwtCbUJ/Fbax1fwi+F8fw902bxD4mh2Xs6NbRWcgSWIqQrK3y5wSVI9qs6leC/vZZ1gjtlfH7mEYVcDHAqfXdYuda1BprlkZ1/djyxgYB/8Ar1n18d+8q" +
            "zdaq7yf4eR9fTpwoQVKnsgoopkkiwxs7kKigsWY4AArYoca5bxp43TwukcUSrNfPtcROGxsO4ZyOOo9awfF3xOe2vFttJZT5TMJndFdX6bdpB6da82uZ2uriSZ/vyMWb6mveweXOfv1dj5/GZlGF6dJ6na3fxav7q1mh" +
            "W1hhaRCgkjZgy5HUc9aztH8fXmnxutzEmplmyHvGZyo9BntXLUV7Msvws4OnKCaZ4kcfiYzVSM2mj2mDxd4Wupo4Uki86Rgir9mb7xOB/DWteeH47qQfv3SMfdjXG1foK8Apdx/vH86+cxXC2AxUeRx0/rtY9/DcSYzD" +
            "vmvqe8yfYPCdm13cSfJux5hTcwz24Ga8i1vxhqOtXk8puZreGVdht45WEeNuDxnvWHuJ6kn8aSvXy3J8NlsOSnE8zMM2r5hLmmwooor3DwwooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKK" +
            "ACiiigAooooAKKKKACiiigAooooAKKKKACiiigApaSlGewyaYHq3wg+DM/i9o9b1U/Y9At8XG90DrdKjkOnDAr9084r2DxB4iSS1i0jS4haaRaJ9njiRtyuikbDyMjAA71heCbia3+CXhdUkeISS3KuqkjI81+D7VFX5" +
            "tXqTxmIlUqu6i2ku1j9AwdGGGoxUN2tX6i0UVmeIvENn4X0W91W+dltLOMyymNdzYHXA71UYuTtFHW9NWWtQ1K20q1a4u5lghUgF2zxnp0rxvXviFqesTTrDK1raSpsNuCGGMYPOO9UfEXjabxlIt1FIyafIgMEallDR" +
            "kllZlJxuwRWJX1mCwCpLnqK7/I+Qx2PlVl7Om7JBRRRXtHiBRRRQIKKKKBhRRRQIKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKK" +
            "ACprT/j6i/31/mKhqnq2s22hWn2m6lMEe4KrqpJ3Hp0HtUVI88HHuaU/jXqfZfxB1K4m1qSydwbaDaYlCgYJRSecZrgdZ8VaL4deJNV1ex015QSi3dykRYDqQGIyPpXxxrH7Qfi3WPDOnac+qahHqNrI7S6r9tczXKk" +
            "nCv/ALoIA5PArhte8Uav4mkil1fU7rUXiBWNrqUuVB5IGenSvksNk8oRUZv7j7yeKvse7+PP2p59Tsks/CkF1pmoLcgNcSpFKskeGGFB3ck7SDivN7XRdc+Ifii51nxGJFlZozcGWEw+dhdo27QAMBR0q/4D+H7WZN/" +
            "qkamUjEcDhXXqpDggnng16F7A8V9JRwtOivdR85isfJtwgRWtvHZ20VvCu2KJAijOeAMCpKKK7DwwooooEFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAF" +
            "FFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABXmnxY1ueO4h0sLH9nZEuN2Dv3ZYdc4xx6V6XXi2uWmueKtXu51glvIoJmt1kjjACgMSBwB0BpM78HFc/NLZHLqpZgoBYk4AUZJNeoeA/AP2N4dUvjul2CSGEfdwynO" +
            "8FevPrWxoPw80vSYrWSSP7Vewt5gucsvO7I+Xdjjj8q6mhI2xGL5k40xfwxSUUUzywooooEFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAU" +
            "UUUAFFFFABRRRQAUUUUAFFFFABRRRQAVFb2dvZhxbwxwh2Lt5ahdzHqTjqfepaKBhRRRQIKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAoo" +
            "ooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooA//2Q==")
    private String imageBase64;

    /**
     * 이미지 파일명
     * @author [이소정]
     */
    @ApiModelProperty(name = "imageFileName", value = "이미지 파일명", required = true, example = "main_img.jpg", hidden = true)
    private String imageFileName;

    /**
     * 이미지 파일 사이즈
     * @author [이소정]
     */
    @ApiModelProperty(name = "imageFileSize", value = "이미지 파일 사이즈", required = true, example = "500", hidden = true)
    private String imageFileSize;

    /**
     * 이미지 파일 물리명
     * @author [이소정]
     */
    @ApiModelProperty(name = "imageFilePhysicalName", value = "이미지 파일 물리명", required = true, example = "8080/cdn/contnets/", hidden = true)
    private String imageFilePhysicalName;

    /**
     * 폴더명
     * @author [이소정]
     */
    @ApiModelProperty(name = "folderName", value = "폴더명", required = true, example = "SP20 NSW NIKE DIRECT AM90")
    private String folderName;

    /**
     * 폴더 내용
     * @author [이소정]
     */
    @ApiModelProperty(name = "folderContents", value = "폴더 내용", required = true, example = "SP20 나이키 다이렉트 NSW 캠페인 시공 에셋 자료")
    private String folderContents;

    /**
     * 캠페인 기간 구분 공통코드
     * @author [이소정]
     */
    @ApiModelProperty(name = "campaignPeriodSectionCode", value = "캠페인 기간 구분 공통코드(날짜선택:SELECT/365:EVERY)", required = true, example = "EVERY")
    private String campaignPeriodSectionCode;

    /**
     * 캠페인 시작 일시
     * @author [이소정]
     */
    @ApiModelProperty(name = "campaignBeginDt", value = "캠페인 시작 일시", example = "2020.06.01")
    private String campaignBeginDt;

    /**
     * 캠페인 종료 일시
     * @author [이소정]
     */
    @ApiModelProperty(name = "campaignEndDt", value = "캠페인 종료 일시", example = "2020.09.01")
    private String campaignEndDt;

    /**
     * 메모
     * @author [이소정]
     */
    @ApiModelProperty(name = "memo", value = "메모", example = "메모 입력\n메모 입력\n메모 입력\n")
    private String memo;

    /**
     * 콘텐트 파일 리스트
     * @author [이소정]
     */
    @ApiModelProperty(name = "contentsFileList", value = "컨텐츠 파일 리스트")
    private List<ContentsFileSaveDTO> contentsFileList;

    /**
     * 노출 여부
     * @author [이소정]
     */
    @ApiModelProperty(name = "exposureYn", value = "폴더 상태(노출 여부 Y/N)", example = "Y")
    private String exposureYn;

//    권한 관련 DTO
    /**
     * 권한 체크 목록
     *
     * @author [오지훈]
     */
    @NotNull(message = "userContents.checks")
    @ApiModelProperty(name = "authChecks", value = "권한 체크 목록")
    private List<UserContentsSaveDTO.AuthCheckDTO> checks = new ArrayList<>();

    /**
     * The Class Auth check.
     *
     * @author [오지훈]
     * @since 2020. 7. 20. 오후 12:23:51
     * @implNote 권한 체크 DTO
     */
    @Getter
    @Setter
    public static class AuthCheckDTO {

        /**
         * The Auth seq
         *
         * @author [오지훈]
         */
        @ApiModelProperty(name = "authSeq", value = "권한 시퀀스")
        private Long authSeq;

        /**
         * The Detail auth yn
         *
         * @author [오지훈]
         */
        @ApiModelProperty(name = "detailAuthYn", value = "상세_권한_여부", example = "N")
        private String detailAuthYn = ServiceCode.YesOrNoEnumCode.N.toString();

        /**
         * The Email reception yn
         *
         * @author [오지훈]
         */
        @ApiModelProperty(name = "emailReceptionYn", value = "메일_수신_여부", example = "N")
        private String emailReceptionYn = ServiceCode.YesOrNoEnumCode.N.toString();

    }
}
