import UIKit
import Flutter

@UIApplicationMain
@objc class AppDelegate: FlutterAppDelegate {
    override func application(
    _ application: UIApplication,
    didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?
    ) -> Bool {

    let controller : FlutterViewController = window?.rootViewController as! FlutterViewController
    let channelFetchAllWeatherData = "com.lanweather.flutterapp/fetchallweatherdata"
    let channel = FlutterMethodChannel(name: channelFetchAllWeatherData, binaryMessager: controller.binaryMessager)

    channel.setMethodCallHandler({
        [weak self] (call: FlutterMethodCall, result: @escaping FlutterResult) -> Void in
        guard call.method == "fetchAllWeatherData" else {
            result(FlutterMethodNotImplemented)
            return
        }
        let url = call.argument["url"] as? String
        let data = call.argument["data"] as? String
        self?.fetchCurrentWeatherData(jsonData: call.)
    })

    GeneratedPluginRegistrant.register(with: self)
    return super.application(application, didFinishLaunchingWithOptions: launchOptions)
    }
    
    private func fetchCurrentWeatherData(jsonData: String) -> String {
        // Get nested JSON object
        let jsonDataObj = JSONSerialization.jsonObject(with: jsonData, options: []) as? [String: Any]
        return String(jsonDataObj["nws"]["current"])
    }
}
