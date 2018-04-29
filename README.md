## BudHook[need update]
BudHook add a similar Xposed api to YAHFA<br>
BudHook based on several projects below:
* [YAHFA](https://github.com/rk700/YAHFA)
* [asmdex](http://asm.ow2.org/doc/tutorial-asmdex.html)
* [TurboDex](https://github.com/asLody/TurboDex)

### [中文[need update]](https://github.com/bmax121/BudHook/new/master)

## What did it solved?
When using YAHFA, every hook of a method, you have to write a method to replace it, if you want to call the original method, you must write another method to save the original method information. This is very troublesome to use.
BudHook generates these methods dynamically. It is very `simple` and to use.
## How it works
When hooking a method, budhook will generate the two methods based on the information of the hooked method automatically, then generate the dex file, and finally load the dex file to complete the hook.
## How to run this demo
* 1.Import the project in Android Studio `(with Instant Run disabled)`
* 2.Build `appplugin` and then put `appplugin-debug.apk` into sdcard
* 3.Run `app`
## API
`It is recommended to run and read this demo first.`
There are several important classes : 
* `BudHelpers`: High-level API for convenient hook.Like `XposedHelper`.
* `BudCallBack`: Provide `beforeCall` and `afterCall` for subclass overrides.Like `XC_MethodHook`
* `BudCallBack.MethodHookParams`: Wraps information about the method call and allows to influence it.Basic types will be boxed or unboxed automatically.
* `BudBridge`: Export two core functions for hook: `hookMethod`,`hookManyMethod`.

## TODO

## LICENSE
