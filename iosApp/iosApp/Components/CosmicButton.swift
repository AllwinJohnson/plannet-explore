import SwiftUI

struct CosmicButton: View {
    let text: String
    let action: () -> Void
    
    var body: some View {
        Button(action: action) {
            Text(text)
                .font(.headline)
                .foregroundColor(.white)
                .padding(.horizontal, 32)
                .padding(.vertical, 16)
                .background(Color(hex: 0x2A5298)) // CosmicBlue
                .clipShape(Capsule())
        }
    }
}
