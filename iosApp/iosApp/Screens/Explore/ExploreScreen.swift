import SwiftUI
import shared

struct ExploreScreen: View {
    // In a real app, we'd inject this
    // @StateObject var store = ...
    // For now, simple mock View structure matching Android
    
    var onNavigateToPlanetList: () -> Void
    
    var body: some View {
        CosmicBackground {
            VStack {
                Text("Anti-Cosmic")
                    .font(.largeTitle)
                    .fontWeight(.bold)
                    .foregroundColor(.white)
                
                Spacer().frame(height: 16)
                
                Text("Explore the Universe")
                    .font(.title2)
                    .foregroundColor(.white.opacity(0.8))
                
                Spacer().frame(height: 32)
                
                CosmicButton(text: "Start Journey") {
                    onNavigateToPlanetList()
                }
            }
        }
    }
}
