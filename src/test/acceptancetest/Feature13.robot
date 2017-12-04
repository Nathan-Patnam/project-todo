| *Setting* | *Value*            |
| Library   | keywords.Feature13 |

| *Variable*                   | *Value*                                                                                                                                    
| ${expected}                  | usage: java VolumeCalculator length width height type\nVolumeCalculator.java: error: the argument(s) type are required              |  
| *Test Case*                  | *Action*                       | *Argument*         | *Argument*   | *Argument* | *Argument* | *Argument* | *Argument* | *Argument* |
| Test Required values         | Start Program With Arguments   | 7                  | --type       | ellipsoid  | 3          | 2          |            |            |
|                              | ${length}=                     | Get Length         |              |            |            |            |            |            |
|                              | Should Be Equal                | 7                  | ${length}    |            |            |            |            |            |
|                              | ${width}=                      | Get Width          |              |            |            |            |            |            |
|                              | Should Be Equal                | 3                  | ${width}     |            |            |            |            |            |
|                              | ${height}=                     | Get Height         |              |            |            |            |            |            |
|                              | Should Be Equal                | 2                  | ${height}    |            |            |            |            |            |
|                              | ${type}=                       | Get Type           |              |            |            |            |            |            |
|                              | Should Be Equal                | ellipsoid          | ${type}      |            |            |            |            |            |
| Test required error          | Start Program With Arguments   | 7                  | 3            | 2          |            |            |            |            |
|                              | ${output}=                     | Get Program Output |              |            |            |            |            |            |      
|                              | Should Be Equal                | ${output}          | ${expected}  |            |            |            |            |            |  
