# Allow the inclusion of additional descriptive information on the program and each argument and provide named "-h" argument that shows usage and help information by default.

## Additional Information

Each argument has a meaning in the context of a given program. That meaning is what is conveyed in the descriptive information.

## Use Cases

Assume VolumeCalculator.java allows for three positional arguments, named `length`, `width`, and `height`, respectively.

    java VolumeCalculator -h

should produce the following output:

    usage: java VolumeCalculator length width height
    Calculate the volume of a box.
    positional arguments:
       length the length of the box
       width the width of the box
       height the height of the box

## Acceptance Tests

    | *Variable*  | *Value*                                                                                                                                                                                                                         |
    | ${expected} | usage: java VolumeCalculator length width height\nCalculate the volume of a box.\npositional arguments:\n   length the length of the box (float)\n   width the width of the box (float)\n   height the height of the box (float) |

    | *Test Case*        | *Action*                     | *Argument*         | *Argument*  |
    | Test Usage Message | Start Program With Arguments | -h                 |             |
    |                    | ${output}=                   | Get Program Output |             |
    |                    | Should Be Equal              | ${expected}        | ${output}   |
