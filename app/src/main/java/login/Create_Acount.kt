package login

import Activity.Admin_Main_Activity
import android.Manifest
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.makeyourstore.R
import com.example.makeyourstore.SQLite_Manage_Your_Store
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import gun0912.tedbottompicker.TedBottomPicker
import gun0912.tedbottompicker.TedBottomPicker.OnImageSelectedListener
import kotlinx.android.synthetic.main.activity_create__a__new__account.*
import kotlinx.android.synthetic.main.activity_create__acount.btnCancle
import kotlinx.android.synthetic.main.activity_create__acount.btnCreateAccount
import kotlinx.android.synthetic.main.activity_create__acount.etAnswer
import kotlinx.android.synthetic.main.activity_create__acount.etNewFullName
import kotlinx.android.synthetic.main.activity_create__acount.etNewPassword
import kotlinx.android.synthetic.main.activity_create__acount.etNewPhone
import kotlinx.android.synthetic.main.activity_create__acount.etNewUserName
import kotlinx.android.synthetic.main.activity_create__acount.etQuestion
import kotlinx.android.synthetic.main.activity_create__acount.tvDateOfBirth
import object_App.Account
import java.io.IOException

class Create_Acount : AppCompatActivity() {
    var sqLite_manage_your_store: SQLite_Manage_Your_Store? = null
    var accountList: List<Account>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create__acount)
        sqLite_manage_your_store = SQLite_Manage_Your_Store(applicationContext)
        val selectedYear = 2000
        val selectedMonth = 5
        val selectedDayOfMonth = 10
        tvDateOfBirth.setOnClickListener(View.OnClickListener {
            val dateSetListener = OnDateSetListener { view, year, monthOfYear, dayOfMonth -> tvDateOfBirth.setText(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year) }
            val datePickerDialog = DatePickerDialog(this,
                    dateSetListener, selectedYear, selectedMonth, selectedDayOfMonth)
            datePickerDialog.show()
        })
        btnCancle.setOnClickListener(View.OnClickListener() {
            fun onClick(v: View?) {
                val intent = Intent(baseContext, Login_Activity::class.java)
                startActivity(intent)
            }
        })
        ivAvatar.setOnClickListener(View.OnClickListener { requestPermissions() })
        btnCreateAccount.setOnClickListener(View.OnClickListener {
            var dem = 0
            val userName: String = etNewUserName.text.toString()
            val password: String = etNewPassword.text.toString()
            val fullName: String = etNewFullName.text.toString()
            val dateOfBirth: String = tvDateOfBirth.text.toString()
            val phone: String = etNewPhone.text.toString()
            val question: String = etQuestion.text.toString()
            val answer: String = etAnswer.text.toString()
            val avatar: String = tvThemAnh.getText().toString()
            val newAccount = Account(0, userName, password, fullName, dateOfBirth, phone, question, answer,avatar ,1)
            if (userName.isEmpty() || password.isEmpty() || fullName.isEmpty() || dateOfBirth.isEmpty() || question.isEmpty() || answer.isEmpty()) {
                Toast.makeText(baseContext, "Bạn nhập thiếu thông tin", Toast.LENGTH_LONG).show()
            } else if (newAccount.checkUserName() && newAccount.checkPassword()) {
                accountList = sqLite_manage_your_store!!.allAccounts
                for (account in (accountList as MutableList<Account>?)!!) {
                    if (userName.equals(account.userName, ignoreCase = true)) {
                        dem++
                    }
                }
                if (dem != 0) {
                    Toast.makeText(baseContext, "Tên đăng nhập đã tồn tại", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(baseContext, "Tạo tài khoản thành công", Toast.LENGTH_LONG).show()
                    sqLite_manage_your_store!!.insertAccount(newAccount)
                    val intent = Intent(baseContext, Admin_Main_Activity::class.java)
                    startActivity(intent)
                }
            }
            if (newAccount.checkUserName() == false) etNewUserName.setError("Tài khoản có từ 6 ký tự bao gồm chữ hoa, chữ thường và số")
            if (newAccount.checkPassword() == false) etNewPassword.setError("Mật khẩu có từ 8 ký tự bao gồm chữ hoa, chữ thường và số")
        })
    }
    private fun requestPermissions() {
        val permissionlistener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                opentImagePicker()
            }

            override fun onPermissionDenied(deniedPermissions: List<String>) {
                Toast.makeText(applicationContext, "Permission Denied\n$deniedPermissions", Toast.LENGTH_SHORT).show()
            }
        }
        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check()
    }

    private fun opentImagePicker() {
        val listener = OnImageSelectedListener { uri ->
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                ivAvatar.setImageBitmap(bitmap)
                tvThemAnh.setText(uri.path)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        val tedBottomPicker = TedBottomPicker.Builder(applicationContext)
                .setOnImageSelectedListener(listener)
                .create()
        tedBottomPicker.show(supportFragmentManager)
    }
}