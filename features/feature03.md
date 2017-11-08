# Allow datatype information to be added to arguments so that non-string arguments can be used. 

## Additional Information

Failure of arguments to match specified datatypes results in program exit and usage information. The following datatypes should be supported: int, float, boolean, and string, which is the default value if type is left unspecified.

## Use Cases

Assume VolumeCalculator.java allows for three positional arguments, named `length`, `width`, and `height`, respectively. Each should be a floating-point number.

    java VolumeCalculator 7 something 2

should produce the following output:

    usage: java VolumeCalculator length width height
    VolumeCalculator.java: error: argument width: invalid float value: something
    
## Acceptance Tests

    | *Variable*  | *Value*                                                                                                                       |
    | ${expected} | usage: java VolumeCalculator length width height\nVolumeCalculator.java: error: argument width: invalid float value: something |
    
    | *Test Case*                     | *Action*                     | *Argument*         | *Argument*  | *Argument*  |
    | Test Incorrect Datatype Message | Start Program With Arguments | 7                  | something   | 2           |
    |                                 | ${output}=                   | Get Program Output |             |             |
    |                                 | Should Be Equal              | ${output}          | ${expected} |             |


