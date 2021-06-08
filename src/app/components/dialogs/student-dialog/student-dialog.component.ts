import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Status } from 'src/app/models/status';
import { Student } from 'src/app/models/student';
import { DepartmanService } from 'src/app/services/departman.service';
import { StatusService } from 'src/app/services/status.service';
import { StudentService } from 'src/app/services/student.service';
import { StudentComponent } from '../../student/student.component';

@Component({
  selector: 'app-student-dialog',
  templateUrl: './student-dialog.component.html',
  styleUrls: ['./student-dialog.component.css']
})
export class StudentDialogComponent implements OnInit {

//departmani: Departman[]
statusi: Status[];
flag: number;

  constructor(public snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<StudentComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Student,
    public studentService: StudentService,
    public statusService: StatusService) { }

  ngOnInit(): void {
    this.statusService.getAllStatus()
    .subscribe(
      statusi => {
this.statusi = statusi;

      }
    ),
    (error:Error) => {
      console.log(error.name + ' -> ' + error.message)
      this.snackBar.open('Dogodila se greska, pokusajte ponovo. ', 'Zatvori', { duration: 2500 });
    }
  }

  compareTo(a,b){
return a.id ===b.id;
  }

  public add(): void{
    this.studentService.addStudent(this.data)
    .subscribe(
      () => {
        this.snackBar.open('Uspesno dodat student: ' + this.data.brojIndeksa, 'U redu', { duration: 2500 });
      },
      (error:Error) => {
        console.log(error.name + ' -> ' + error.message)
        this.snackBar.open('Dogodila se greska, pokusajte ponovo. ', 'Zatvori', { duration: 2500 });
      }
    )
  }

  public update(): void{
    this.studentService.updateStudent(this.data)
    .subscribe(
      () => {
        this.snackBar.open('Uspešno izmenjen student', 'U redu', { duration: 2500 });
      }
    ),
    (error:Error) => {
      console.log(error.name + ' -> ' + error.message)
      this.snackBar.open('Dogodila se greska, pokusajte ponovo. ', 'Zatvori', { duration: 2500 });
    }
  }

  public delete(): void{
    this.studentService.deleteStudent(this.data.id)
    .subscribe(
      () => {
        this.snackBar.open('Uspešno izbrisan student', 'U redu', { duration: 2500 });
      }
    ),
    (error:Error) => {
      console.log(error.name + ' -> ' + error.message)
      this.snackBar.open('Dogodila se greska, pokusajte ponovo. ', 'Zatvori', { duration: 2500 });
    }
  }

public close(): void{
  this.dialogRef.close();
  this.snackBar.open('Odustali ste', 'U redu', { duration: 1000 });
}

}
