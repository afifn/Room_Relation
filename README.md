# Room_Relation 
source from [codelab dicoding](https://github.com/dicodingacademy/a352-android-intermediate-labs/tree/main/advanced-database/LatihanRelasiRoom/MyStudentData%20(Starter))

## Learn
- Relation one to many
- Relation many to one
- Relation many to many


#### StudentEntity.kt
```
// add new table 
@Entity(primaryKeys = ["sId","cId"])
data class CourseStudentCrossRef(
    val sId: Int,
    @ColumnInfo(index = true)
    val cId: Int
)
```
```
// relasi many to one
data class StudentAndUniversity(
    @Embedded
    val student: Student,

    @Relation(
        parentColumn = "univId",
        entityColumn = "universityId"
    )
    val university: University
)
```
```
// relasi one to many
data class UniveristyAndStudent(
    @Embedded
    val university: University,

    @Relation(
        parentColumn = "universityId",
        entityColumn = "univId"
    )
    val student: List<Student>
)
```
```
// relasi many to many
data class StudentWithCourse(
    @Embedded
    val student: Student,

    @Relation(
        parentColumn = "studentId",
        entity = Course::class,
        entityColumn = "courseId",
        associateBy = Junction(
            value = CourseStudentCrossRef::class,
            parentColumn = "sId",
            entityColumn = "cId"
        )
    )
    val course: List<Course>
)
```
#### StudentDao.kt
```
@Transaction
@Query("SELECT * FROM student")
fun getAllStudentAndUniversity(): LiveData<List<StudentAndUniversity>>

@Transaction
@Query("SELECT * FROM university")
fun getAllUniveristyAndStudent(): LiveData<List<UniversityAndStudent>>

@Transaction
@Query("SELECT * FROM student")
fun getAllStudentWithCourse(): LiveData<List<StudentWithCourse>>
```
    
#### StudentRepository.kt
```
fun getAllStudentAndUniversity(): LiveData<List<StudentAndUniversity>> = studentDao.getAllStudentAndUniversity()
fun getAllUniversityAndStudent(): LiveData<List<UniversityAndStudent>> = studentDao.getAllUniveristyAndStudent()
fun getAllStudentWithCourse(): LiveData<List<StudentWithCourse>> = studentDao.getAllStudentWithCourse()
```

#### MainViewModel.kt
```
fun getAllStudentAndUniversity(): LiveData<List<StudentAndUniversity>> = studentRepository.getAllStudentAndUniversity()
fun getAllUniversityAndStudent(): LiveData<List<UniversityAndStudent>> = studentRepository.getAllUniversityAndStudent()
fun getAllStudentWithCourse(): LiveData<List<StudentWithCourse>> = studentRepository.getAllStudentWithCourse()
```

> create Adapter

#### MainActivity.kt
```
private fun getStudentAndUniversity() {
        val adapter = StudentAndUniversityAdapter()
        binding.rvStudent.adapter = adapter
        mainViewModel.getAllStudentAndUniversity().observe(this) {
            adapter.submitList(it)
            Log.d(TAG, "getStudentAndUniversity: $it")
        }
    }

    private fun getUniversityAndStudent() {
        val adapter = UniveristyAndStudentAdapter()
        binding.rvStudent.adapter = adapter
        mainViewModel.getAllUniversityAndStudent().observe(this) {
            adapter.submitList(it)
        }
    }


    private fun getStudentWithCourse() {
        val adapter = StudentWithCourseAdapter()
        binding.rvStudent.adapter = adapter
        mainViewModel.getAllStudentWithCourse().observe(this) {
            adapter.submitList(it)
        }
    }
```
