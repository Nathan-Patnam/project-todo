| *Setting* | *Value*            |
| Library   | keywords.Feature08 |

| *Variable*          | *Value*                                                                                                                                    
| ${unknownArg}       | argument myarg does not exist                                                                        |
| ${wrongDataType}    | usage: java VolumeCalculator length width height\nVolumeCalculator.java: error: argument width: invalid float value: something |


| *Test Case*                                       | *Action*                                           | *Argument*                      | *Argument*   | *Argument* | *Argument* | *Argument* |
| Test Unrecognized Argument                        | Start Program With Nonexistent Argument            | 7                               | 5            | --myarg    | myval      | 2          |
|                                                   | ${output}=                                         | Get Nonexistent Program Output  |              |            |            |            |
|                                                   | Should Be Equal                                    | ${unknownArg}                   | ${output}    |            |            |            |
| Test Bad Datatype Argument                        | Start Program With Bad Datatype Argument           | 7                               | something    | 2          |            |            |
|                                                   | ${output}=                                         | Get Bad Datatype Program Output |              |            |            |            |
|                                                   | Should Be Equal                                    | ${wrongDataType}                | ${output}    |            |            |            |
