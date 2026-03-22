import 'package:flutter/material.dart';
import 'package:lanweatherapp/services/preferences.dart';
import 'package:lanweatherapp/strings.dart';

class Settings extends StatefulWidget {
  const Settings({super.key});

  @override
  _SettingsState createState() => _SettingsState();
}

class _SettingsState extends State<Settings> {
  late TextEditingController textEditingController;

  @override
  void initState() {
    super.initState();
    textEditingController = TextEditingController(text: Preferences.getWeatherServerUrl());
  }

  @override
  void dispose() {
    textEditingController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    bool isMobile = MediaQuery.of(context).size.width < 600;

    return ListView(
      padding: EdgeInsets.symmetric(vertical: isMobile ? 40.0 : 20.0, horizontal: 20.0),
      children: [
        Container(
          margin: const EdgeInsets.only(top: 18.0, bottom: 25.0),
          alignment: Alignment.center,
          child: Text(Strings.settings, style: TextStyle(color: Colors.white, fontSize: 40)),
        ),
        TextField(
          controller: textEditingController,
          cursorColor: Colors.blue,
          decoration: const InputDecoration(
            label: Text("Weather server URL", style: TextStyle(color: Colors.white)),
            enabledBorder: OutlineInputBorder(borderSide: BorderSide(color: Colors.white, width: 2.0)),
            focusedBorder: OutlineInputBorder(borderSide: BorderSide(color: Colors.white, width: 3.0)),
            hintStyle: TextStyle(color: Colors.white),
          ),
          onSubmitted: (String value) {
            Preferences.setWeatherServerUrl(value);
          },
          style: TextStyle(color: Colors.white),
        ),
      ],
    );
  }
}
