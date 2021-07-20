import UIKit
import Flutter
import SwiftyZeroMQ5

@UIApplicationMain
@objc class AppDelegate: FlutterAppDelegate {
    override func application(
    _ application: UIApplication,
    didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?
    ) -> Bool {

        let controller : FlutterViewController = window?.rootViewController as! FlutterViewController
        let channelFetchAllWeatherData = "com.lanweather.flutterapp/fetchallweatherdata"
        let channel = FlutterMethodChannel(name: channelFetchAllWeatherData, binaryMessenger: controller.binaryMessenger)

        channel.setMethodCallHandler({
            [weak self] (call: FlutterMethodCall, result: @escaping FlutterResult) -> Void in
            guard call.method == "fetchAllWeatherData" else {
                result(FlutterMethodNotImplemented)
                return
            }
            let arguments = call.arguments as! Dictionary<String, String>
            let url = arguments["url"]
            let data = arguments["data"]
            
            var jsonData: String?
            do {
                let context = try SwiftyZeroMQ.Context()
                let socket = try context.socket(SwiftyZeroMQ.SocketType.request)
                try socket.connect(url!)
                try socket.send(string: data!)
                jsonData = try socket.recv()
                try socket.close()
            } catch {
                print(error)
            }
            
            let jsonDataObj = self?.getCurrentWeatherData(jsonData: jsonData!)
            result(jsonDataObj)
        })

        GeneratedPluginRegistrant.register(with: self)
        return super.application(application, didFinishLaunchingWithOptions: launchOptions)
    }
    
    private func getCurrentWeatherData(jsonData: String) -> String {
        // Get nested JSON object
//        let jsonDataObj = JSONSerialization.jsonObject(with: jsonData, options: JSONSerialization.ReadingOptions.mutableContainers) as? [String: Any]
//        return String(jsonDataObj["nws"]["current"])
        return ""
    }
}
