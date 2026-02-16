import SwiftUI
import shared

struct PlanetListScreen: View {
    // Mock data for UI structure
    let planets = [
        Planet(id: "mercury", name: "Mercury", description: "Hot", imageUrl: "", distanceFromSun: "58m", gravity: "3.7", temperature: "167"),
        Planet(id: "venus", name: "Venus", description: "Hotter", imageUrl: "", distanceFromSun: "108m", gravity: "8.8", temperature: "464"),
        Planet(id: "earth", name: "Earth", description: "Home", imageUrl: "", distanceFromSun: "150m", gravity: "9.8", temperature: "15"),
        Planet(id: "mars", name: "Mars", description: "Red", imageUrl: "", distanceFromSun: "228m", gravity: "3.7", temperature: "-65")
    ]
    
    var onPlanetSelected: (String) -> Void
    
    let columns = [
        GridItem(.flexible()),
        GridItem(.flexible())
    ]
    
    var body: some View {
        CosmicBackground {
            VStack(alignment: .leading) {
                Text("Solar System")
                    .font(.largeTitle)
                    .fontWeight(.bold)
                    .foregroundColor(.white)
                    .padding()
                
                ScrollView {
                    LazyVGrid(columns: columns, spacing: 16) {
                        ForEach(planets, id: \.id) { planet in
                            PlanetItemView(planet: planet) {
                                onPlanetSelected(planet.id)
                            }
                        }
                    }
                    .padding()
                }
            }
        }
    }
}

struct PlanetItemView: View {
    let planet: Planet
    let onTap: () -> Void
    
    var body: some View {
        Button(action: onTap) {
            VStack {
                Circle()
                    .fill(Color.white.opacity(0.2))
                    .frame(width: 80, height: 80)
                
                Spacer().frame(height: 16)
                
                Text(planet.name)
                    .font(.headline)
                    .foregroundColor(.white)
            }
            .padding()
            .frame(maxWidth: .infinity)
            .aspectRatio(0.8, contentMode: .fit)
            .background(
                LinearGradient(
                    gradient: Gradient(colors: [Color(hex: 0x2D0B73), Color(hex: 0x6A1B9A)]),
                    startPoint: .top,
                    endPoint: .bottom
                )
            )
            .cornerRadius(24)
        }
    }
}
