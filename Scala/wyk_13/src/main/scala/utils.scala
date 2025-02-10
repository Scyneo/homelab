package zjp

private def waitForEnter(): Unit = {
  println(
  """
  Naciśnij ENTER żeby zakończyć …
  """
  )
  scala.io.StdIn.readLine()
}
