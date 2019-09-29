package scalaSchool.concurrency

import java.util.concurrent.{Callable, FutureTask}

import scala.concurrent.Await

/**
  * @Author: chenjinxin
  * @Date: Created in 下午6:02 19-9-27
  * @Description:
  */
object Demo extends App {

  println("--------------线程---------------")
  val hello = new Thread(new Runnable {
    def run() {
      println("hello world")
    }
  })
  hello.start
  println("--------------单线程代码---------------")
  //  import java.net.{Socket, ServerSocket}
  //  import java.util.concurrent.{Executors, ExecutorService}
  //  import java.util.Date
  //
  //  class NetworkService(port: Int, poolSize: Int) extends Runnable {
  //    val serverSocket = new ServerSocket(port)
  //
  //    def run() {
  //      while (true) {
  //        // This will block until a connection comes in.
  //        val socket = serverSocket.accept()
  //        (new Handler(socket)).run()
  //      }
  //    }
  //  }
  //
  //  class Handler(socket: Socket) extends Runnable {
  //    def message = (Thread.currentThread.getName() + "\n").getBytes
  //
  //    def run() {
  //      socket.getOutputStream.write(message)
  //      socket.getOutputStream.close()
  //    }
  //  }

  //  (new NetworkService(2020, 2)).run
  println("------------Executors-----------------")


  import java.net.{Socket, ServerSocket}
  import java.util.concurrent.{Executors, ExecutorService}
  import java.util.Date

  class NetworkService(port: Int, poolSize: Int) extends Runnable {
    val serverSocket = new ServerSocket(port)
    val pool: ExecutorService = Executors.newFixedThreadPool(poolSize)

    def run() {
      try {
        while (true) {
          // This will block until a connection comes in.
          val socket = serverSocket.accept()
          pool.execute(new Handler(socket))
        }
      } finally {
        pool.shutdown()
      }
    }
  }

  class Handler(socket: Socket) extends Runnable {
    def message = (Thread.currentThread.getName() + "\n").getBytes

    def run() {
      socket.getOutputStream.write(message)
      socket.getOutputStream.close()
    }
  }

  (new NetworkService(2020, 2)).run

  println("-----------------------------")


  println("-----------------------------")

}
