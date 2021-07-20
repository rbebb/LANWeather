import 'package:flutter/material.dart';
import 'package:flutterapp/strings.dart';

class BottomNavigationBarCustom extends StatefulWidget {
  final ValueSetter<int> callback;
  BottomNavigationBarCustom({required this.callback});

  @override
  _BottomNavigationBarCustomState createState() =>
      _BottomNavigationBarCustomState();
}

class _BottomNavigationBarCustomState extends State<BottomNavigationBarCustom> {
  int _selectedIndex = 0;

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });
  }

  @override
  Widget build(BuildContext context) {
    return BottomNavigationBar(
      items: const <BottomNavigationBarItem>[
        BottomNavigationBarItem(
          icon: Icon(Icons.home),
          label: Strings.home,
        ),
        BottomNavigationBarItem(
          icon: Icon(Icons.cloud),
          label: Strings.forecast,
        ),
        BottomNavigationBarItem(
          icon: Icon(Icons.settings),
          label: Strings.settings,
        )
      ],
      currentIndex: _selectedIndex,
      selectedItemColor: Colors.blue[800],
      onTap: (index) {
        _onItemTapped(index);
        widget.callback(index);
      },
    );
  }
}
