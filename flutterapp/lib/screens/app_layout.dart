import 'package:flutter/material.dart';
import 'package:flutterapp/components/bottom_navigation_bar_custom.dart';
import 'package:flutterapp/screens/forecast.dart';
import 'package:flutterapp/screens/home.dart';
import 'package:flutterapp/screens/settings.dart';

class AppLayout extends StatefulWidget {
  @override
  _AppLayoutState createState() => _AppLayoutState();
}

class _AppLayoutState extends State<AppLayout> {
  int _selectedIndex = 0;

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Stack(
        children: [
          Image.asset(
            'assets/images/home.png',
            height: MediaQuery.of(context).size.height,
            width: MediaQuery.of(context).size.width,
            fit: BoxFit.cover,
          ),
          IndexedStack(
            index: _selectedIndex,
            children: [
              Home(),
              Forecast(),
              Settings(),
            ],
          )
        ],
      ),
      bottomNavigationBar: BottomNavigationBarCustom(callback: _onItemTapped),
    );
  }
}
