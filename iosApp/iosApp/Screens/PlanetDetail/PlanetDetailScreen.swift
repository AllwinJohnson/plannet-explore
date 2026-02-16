import SwiftUI
import shared

struct PlanetDetailScreen: View {
    let planetId: String
    var onBack: () -> Void
    
    // Mock fetching for UI dev
    @State var planet: Planet? = Planet(id: "mars", name: "Mars", description: "Dusty, cold, desert world with a very thin atmosphere. There is strong evidence that Mars was—billions of years ago—wetter and warmer, with a thick atmosphere.", imageUrl: "", distanceFromSun: "228 million km", gravity: "3.71 m/s²", temperature: "-65°C")
    
    var body: some View {
        CosmicBackground {
            VStack(alignment: .leading) {
                // Back Button
                Button(action: onBack) {
                    Image(systemName: "arrow.left")
                        .foregroundColor(.white)
                        .padding()
                }
                
                if let planet = planet {
                    ScrollView {
                        VStack(alignment: .leading, spacing: 24) {
                            Text(planet.name)
                                .font(.system(size: 32, weight: .bold))
                                .foregroundColor(.white)
                            
                            Text(planet.description_)
                                .font(.body)
                                .foregroundColor(.white.opacity(0.9))
                            
                            Divider().background(Color.white.opacity(0.2))
                            
                            DetailRow(label: "Distance", value: planet.distanceFromSun)
                            DetailRow(label: "Gravity", value: planet.gravity)
                            DetailRow(label: "Temperature", value: planet.temperature)
                        }
                        .padding(24)
                    }
                } else {
                    Text("Loading...")
                        .foregroundColor(.white)
                }
            }
        }
    }
}

struct DetailRow: View {
    let label: String
    let value: String
    
    var body: some View {
        VStack(alignment: .leading) {
            Text(label)
                .font(.caption)
                .foregroundColor(.white.opacity(0.7))
            Text(value)
                .font(.title3)
                .foregroundColor(.white)
        }
    }
}
