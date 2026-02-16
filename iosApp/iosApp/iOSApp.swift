import SwiftUI
import shared

@main
struct iOSApp: App {
    
    @State var currentScreen: Screen = .explore
    @State var selectedPlanetId: String? = nil
    
    enum Screen {
        case explore
        case planetList
        case planetDetail
    }
    
    var body: some Scene {
        WindowGroup {
            ZStack {
                switch currentScreen {
                case .explore:
                    ExploreScreen {
                        withAnimation {
                            currentScreen = .planetList
                        }
                    }
                case .planetList:
                    PlanetListScreen { id in
                        selectedPlanetId = id
                        withAnimation {
                            currentScreen = .planetDetail
                        }
                    }
                case .planetDetail:
                    if let id = selectedPlanetId {
                        PlanetDetailScreen(planetId: id) {
                            withAnimation {
                                currentScreen = .planetList
                            }
                        }
                    }
                }
            }
        }
    }
}
