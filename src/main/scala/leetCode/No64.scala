package leetCode

/**
  * @Author: chenjinxin
  * @Date: Created in 上午11:04 19-9-29
  * @Description:
  */
object No64 {
  def minPathSum(dim: Array[Array[Int]]): Int = {
    if (dim.length==0) 0
    else if (dim.length==1){
      dim(0).sum
    }else{
      val mul = List.tabulate(dim.length, dim(1).length) {
        (x, y) => {
          if (x== 0 && y == 0) {
            dim(x)(y)
          } else if (x == 0) {
            dim(x)(y) = dim(x)(y) + dim(x)(y - 1)
            dim(x)(y)
          } else if (y == 0) {
            dim(x)(y) = dim(x)(y) + dim(x - 1)(y)
            dim(x)(y)
          }
          else {
            dim(x)(y) = dim(x)(y) + math.min(dim(x - 1)(y), dim(x)(y - 1))
            dim(x)(y)
          }
        }
      }
      mul(dim.length-1)(dim(0).length-1)
    }
  }
}
