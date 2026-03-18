import 'package:flutter/material.dart';
import 'package:flutterapp/strings.dart';

class Settings extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    bool isMobile = MediaQuery.of(context).size.width < 600;

    return isMobile
        ? ListView(
            padding: const EdgeInsets.symmetric(vertical: 40.0),
            children: [
              Container(
                margin: const EdgeInsets.only(top: 18.0),
                alignment: Alignment.center,
                child: Text(Strings.settings, style: TextStyle(color: Colors.white, fontSize: 40)),
              ),
            ],
          )
        : ListView(
            padding: const EdgeInsets.symmetric(vertical: 20.0),
            children: [
              Container(
                margin: const EdgeInsets.only(top: 18.0),
                alignment: Alignment.center,
                child: Text(Strings.settings, style: TextStyle(color: Colors.white, fontSize: 40)),
              ),
            ],
          );
  }
}
