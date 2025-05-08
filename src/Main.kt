import java.io.File
import kotlin.math.sqrt
import kotlin.random.Random

fun randomDouble(): Double = Random.nextDouble()

fun main() {
    val imageWidth      = 800
    val imageHeight     = 400
    val aspectRatio     = imageWidth.toDouble() / imageHeight.toDouble()
    val samplesPerPixel = 300
    val maxDepth        = 50

    val viewportHeight = 2.0
    val viewportWidth  = aspectRatio * viewportHeight
    val camera = Camera.basicCamera(viewportWidth, viewportHeight, focalLength = 1.0)
    val world = Scene(listOf(
        Sphere(Vector3D(0.0,  -0.5, -1.0), 0.3, Metal(Vector3D(.1, .1, 1.0), fuzz = 0.5)),
        Torus(Vector3D(0.0,  0.0, -1.0),Vector3D(0.0,.0,5.0),.25,.1, Metal(Vector3D(.1, .1, 1.0), fuzz = 0.0)),
        Sphere(Vector3D(0.0, -100.5, -1.0),100.0, Lambertian(Vector3D(.073, .161, .053))),

    ))

    val sunDirection = Vector3D(2.0, 2.0, -2.0).unit()
    val sunLight     = DirectionalLight(
        direction = sunDirection,
        intensity = Vector3D(1.0, 1.0, 1.0)  // boost intensity if you want brighter specular
    )
    val sunLight2 = DirectionalLight(
        direction = Vector3D(-2.0, 2.0, -1.0),
        intensity = Vector3D(3.0, 3.0, 3.0)  // boost intensity if you want brighter specular
    )
    val lights = listOf(sunLight, sunLight2)

    val out = StringBuilder().apply {
        append("P3\n$imageWidth $imageHeight\n255\n")
    }

    for (y in imageHeight - 1 downTo 0) {
        for (x in 0 until imageWidth) {
            var pixelColor = Vector3D(0.0, 0.0, 0.0)
            repeat(samplesPerPixel) {
                val u = (x + randomDouble()) / (imageWidth - 1)
                val v = (y + randomDouble()) / (imageHeight - 1)
                val ray = camera.getRay(u, v)
                pixelColor += ray.rayColor(world, lights, maxDepth)
            }
            val scale = 1.0 / samplesPerPixel
            val scaled = pixelColor * scale

            val clamped = Vector3D(
                scaled.x.coerceIn(0.0, 1.0),
                scaled.y.coerceIn(0.0, 1.0),
                scaled.z.coerceIn(0.0, 1.0)
            )
            val r = (255.999 * sqrt(clamped.x)).toInt()
            val g = (255.999 * sqrt(clamped.y)).toInt()
            val b = (255.999 * sqrt(clamped.z)).toInt()
            out.append("$r $g $b ")
        }
        out.append("\n")
    }

    File("output.ppm").writeText(out.toString())
    println("Rendered output.ppm")
}
