MACRO
INCR1       &FIRST,&SECOND=DATA9
A           1,&FIRST
L           2,&SECOND
MEND
MACRO
INCR2       &ARG1,&ARG2=DATA5
L           3,&ARG1
ST          4,&ARG2
MEND
PRG2        START
            USING                   *,BASE
            INCR1                   DATA1
            INCR2                   DATA3,DATA4
FOUR        DC                      F'4'
FIVE        DC                      F'5'
BASE        EQU                     8
TEMP        DS                      1F
            DROP                    8
            END

