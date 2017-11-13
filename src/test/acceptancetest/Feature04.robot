| *Setting* | *Value*            |
| Library   | keywords.Feature04 |

| *Test Case*                  | *Action*                       | *Argument*         | *Argument*   | *Argument* | *Argument* | *Argument* | *Argument* | *Argument* |
| Test Named Argument Defaults | Start Program With Arguments   | 7                  | 3            | 2          |            |            |            |            |
|                              | ${length}=                     | Get Length         |              |            |            |            |            |            |
|                              | Should Be Equal                | 7                  | ${length}    |            |            |            |            |            |
|                              | ${width}=                      | Get Width          |              |            |            |            |            |            |
|                              | Should Be Equal                | 3                  | ${width}     |            |            |            |            |            |
|                              | ${height}=                     | Get Height         |              |            |            |            |            |            |
|                              | Should Be Equal                | 2                  | ${height}    |            |            |            |            |            |
|                              | ${type}=                       | Get Type           |              |            |            |            |            |            |
|                              | Should Be Equal                | box                | ${type}      |            |            |            |            |            |
|                              | ${digits}=                     | Get Digits         |              |            |            |            |            |            |
|                              | Should Be Equal                | 4                  | ${digits}    |            |            |            |            |            |
| Test Named Argument Single   | Start Program With Arguments   | 7                  | 3            | 2          | --type     | ellipsoid  |            |            |
|                              | ${length}=                     | Get Length         |              |            |            |            |            |            |
|                              | Should Be Equal                | 7                  | ${length}    |            |            |            |            |            |
|                              | ${width}=                      | Get Width          |              |            |            |            |            |            |
|                              | Should Be Equal                | 3                  | ${width}     |            |            |            |            |            |
|                              | ${height}=                     | Get Height         |              |            |            |            |            |            |
|                              | Should Be Equal                | 2                  | ${height}    |            |            |            |            |            |
|                              | ${type}=                       | Get Type           |              |            |            |            |            |            |
|                              | Should Be Equal                | ellipsoid          | ${type}      |            |            |            |            |            |
|                              | ${digits}=                     | Get Digits         |              |            |            |            |            |            |
|                              | Should Be Equal                | 4                  | ${digits}    |            |            |            |            |            |
| Test Named Argument Multiple | Start Program With Arguments   | 7                  | 3            | 2          | --type     | ellipsoid  | --digits   | 1          |
|                              | ${length}=                     | Get Length         |              |            |            |            |            |            |
|                              | Should Be Equal                | 7                  | ${length}    |            |            |            |            |            |
|                              | ${width}=                      | Get Width          |              |            |            |            |            |            |
|                              | Should Be Equal                | 3                  | ${width}     |            |            |            |            |            |
|                              | ${height}=                     | Get Height         |              |            |            |            |            |            |
|                              | Should Be Equal                | 2                  | ${height}    |            |            |            |            |            |
|                              | ${type}=                       | Get Type           |              |            |            |            |            |            |
|                              | Should Be Equal                | ellipsoid          | ${type}      |            |            |            |            |            |
|                              | ${digits}=                     | Get Digits         |              |            |            |            |            |            |
|                              | Should Be Equal                | 1                  | ${digits}    |            |            |            |            |            |
