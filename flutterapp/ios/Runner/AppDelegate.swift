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
                print("yeet0")
                let context = try SwiftyZeroMQ.Context()
                let socket = try context.socket(.request)
                print("yeet1")
                print(url!)
                print(data!)
                try socket.connect(url!)
                print("yeet2")
                try socket.send(string: data!)
                print("yeet3")
                jsonData = try socket.recv()
                print(jsonData)
                print("yeet4")
                try socket.close()
                try context.terminate()
                print("yeet5")
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
        let completeJsonDataObj = try? JSONSerialization.jsonObject(with: jsonData.data(using: .utf8)!, options: []) as? [String : Any]
        let finalJsonDataObj = (completeJsonDataObj?["nws"] as? [String : Any])?["current"]
        print(String(describing: finalJsonDataObj))
        return String(describing: finalJsonDataObj)
    }
}
