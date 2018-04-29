## BudHook
BudHook add a similar Xposed api to YAHFA<br>
BudHook based on several projects below:
* [YAHFA](https://github.com/rk700/YAHFA)
* [asmdex](http://asm.ow2.org/doc/tutorial-asmdex.html)
* [TurboDex](https://github.com/asLody/TurboDex)

### [中文[need update]](https://github.com/bmax121/BudHook/blob/master/Readme_CN.md)

## What did it solved
When using YAHFA, every hook of a method, you have to write a method to replace it, if you want to call the original method, you must write another method to save the original method information. This is very troublesome to use.
BudHook generates these methods dynamically. It is very `simple` to use.
## How it works
When hooking a method, budhook will generate the two methods based on the information of the hooked method automatically, then generate the dex file, and finally load the dex file to complete the hook.
## How to run this demo
* 1.Import the project in Android Studio `(with Instant Run disabled)`
* 2.Build `appplugin` and then put `appplugin-debug.apk` into sdcard
* 3.Run `app`
## Support
BudHook added a layer written by Java to YAHFA,so BudHook supports what YAHFA support.Android 5.0 - 8.1.
## API
`It is recommended to run and read this demo first.`
There are several important classes : 
* `BudHelpers`: High-level API for convenient hook.Like `XposedHelper`.
* `BudBridge`: Export two core functions for hook: `hookMethod`,`hookManyMethod`.
* `BudCallBack`: Provide `beforeCall` and `afterCall` for subclass overrides.Like `XC_MethodHook`
* `BudCallBack.MethodHookParams`: Wraps information about the method call and allows to influence it.Basic types will be boxed or unboxed automatically.
## Note
* TurboDex is useful to avoid some problem that caused by optimization.
* Notice the capacity initialized of YAHFA.
* Get a Context when BudHook initialized only to get a ClassLoader and a place to write and read Dex file,change the source as your like.
