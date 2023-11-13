## How do I create a new LED mode?
We've made it simple and easy to create LED modes with this subsystem. Just follow the following steps:
1. Create a new Java class in the `modes` directory.
2. Extend that class from the `LEDMode` class.
3. Add any code you want to repeat every 20ms to the periodic section! This should update your LED's constantly when they're on that mode.
4. Add any code you want to run initially into the initialize section.

You're done! Our goal was to make this as simple and easy to do as a beginner and with limited Java knowledge.

## How do I create a new segment of LEDs?
Creating a new segment could not be easier. Simply just do the following:
1. Open the `LEDSegment` class in this subsystem directory.
2. Add a new segment with a proper name at the top of the class with the rest of the segments.
3. Set the index to be one more than the last segment, or set it to 0 if it's the only segment.
4. Set the default mode to be something like `SolidRed` or to another mode you've created.
5. Add the following block of code to the initialize function in the `LEDSubsystem` class, be sure to replace segment name with the name of your segment as specified previously :
```
LEDConstants.ledSegments.add(LEDSegment.<segment name>);
```

You're all set now! You've successfully created a new segment of LEDs for your robot.

## I'm getting an "Invalid LED segment" error, how do I fix it?
It seems that the index for the segment is higher than the number of segments specified in the `LEDConstants` class. Be aware that an index is different than the actual number of segments, it will always be one less than what the segment number is.

Be sure to either change the value of the number of segments, or update the index of your segment.

## Why is my LED segment not turning on?
This could be for several reason. Please see the following for possible causes:
- Does your console have any errors in it regarding LEDs?
- Is the segment registered and added to the ledSegments array in the initialize function in `LEDSubsystem` class?
- Is the segment created in the `LEDSegment` class and have a valid index and LED mode?
- Is the number of LEDs per segment the actual number on the physical LED strip?
- Is the number of segments the correct number?

Hopefully this gets the main points of failure. If for some reason the issue is due to our codebase, feel free to open an issue via our [issue tracker](https://github.com/Simbotics/Simbot-Base/issues). Be sure to follow our issue template when opening an issue.