package FromJavaToScala

/**
  * @Author: gin
  * @Date: Created in 下午3:27 19-7-12
  * @Description:
  */
class Parameters {
  /**
    * 变长参数，实际定义了一个同类型的变长数组
    *
    * @param values
    * @return
    */
  def max(values: Int*) = values.foldLeft(values(0)) {
    Math.max
  }
}
