import 'package:flutter/material.dart';
import 'package:flutterapp/screens/forecast.dart';
import 'package:flutterapp/screens/home.dart';
import 'package:flutterapp/screens/settings.dart';
import 'package:flutterapp/strings.dart';

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
    bool isMobile = MediaQuery.of(context).size.width < 600;

    return isMobile
        ? Scaffold(
            body: Stack(
              children: [
                Image.asset(
                  "assets/images/home.png",
                  height: MediaQuery.of(context).size.height,
                  width: MediaQuery.of(context).size.width,
                  fit: BoxFit.cover,
                ),
                IndexedStack(index: _selectedIndex, children: [Home(), Forecast(), Settings()]),
              ],
            ),
            bottomNavigationBar: BottomNavigationBar(
              items: const [
                BottomNavigationBarItem(icon: const Icon(Icons.home), label: Strings.home),
                BottomNavigationBarItem(icon: const Icon(Icons.cloud), label: Strings.forecast),
                BottomNavigationBarItem(icon: const Icon(Icons.settings), label: Strings.settings),
              ],
              currentIndex: _selectedIndex,
              onTap: (index) {
                _onItemTapped(index);
              },
            ),
          )
        : Scaffold(
            body: Stack(
              children: [
                Image.asset(
                  "assets/images/home.png",
                  height: MediaQuery.of(context).size.height,
                  width: MediaQuery.of(context).size.width,
                  fit: BoxFit.cover,
                ),
                Row(
                  children: [
                    NavigationRail(
                      selectedIndex: _selectedIndex,
                      onDestinationSelected: (int index) {
                        _onItemTapped(index);
                      },
                      destinations: const [
                        NavigationRailDestination(icon: const Icon(Icons.home), label: Text(Strings.home)),
                        NavigationRailDestination(icon: const Icon(Icons.cloud), label: Text(Strings.forecast)),
                        NavigationRailDestination(icon: const Icon(Icons.settings), label: Text(Strings.settings)),
                      ],
                      labelType: NavigationRailLabelType.all,
                    ),
                    const VerticalDivider(thickness: 1, width: 1),
                    Expanded(
                      child: IndexedStack(index: _selectedIndex, children: [Home(), Forecast(), Settings()]),
                    ),
                  ],
                ),
              ],
            ),
          );
  }
}
