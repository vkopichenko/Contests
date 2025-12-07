fun main() = println(
    generateSequence(::readLine).toList().run {
        drop(1).fold(0 to elementAt(0).map { it == 'S' }) { (beamCount, beams), line ->
            beamCount + beams.zip(line.asIterable()).count { it.first && it.second == '^' } to
                beams.mapIndexed { i, it -> it && line[i] != '^' || i > 0 && beams[i - 1] && line[i - 1] == '^' || i < beams.lastIndex && beams[i + 1] && line[i + 1] == '^' }
        }.first
    }
)
