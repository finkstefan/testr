import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatDialog, MAT_DIALOG_SCROLL_STRATEGY_FACTORY } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Subscription } from 'rxjs';
import { Departman } from 'src/app/models/departman';
import { Fakultet } from 'src/app/models/fakultet';
import { DepartmanService } from 'src/app/services/departman.service';
import { DepartmanDialogComponent } from '../dialogs/departman-dialog/departman-dialog.component';

@Component({
  selector: 'app-departman',
  templateUrl: './departman.component.html',
  styleUrls: ['./departman.component.css']
})
export class DepartmanComponent implements OnInit, OnDestroy {

displayedColumns = ["id","naziv","oznaka","fakultet","actions"];
dataSource: MatTableDataSource<Departman>;
departmanSubscription: Subscription;
selektovaniDepartman: Departman;
@ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;
@ViewChild(MatSort,{static: false}) sort: MatSort;

  constructor(public departmanService:DepartmanService, public dialog:MatDialog) { }

  ngOnInit(): void {
    this.loadData();
  }

  ngOnDestroy(): void{
    this.departmanSubscription.unsubscribe;
  }

  public loadData(){
this.departmanSubscription = this.departmanService.getAllDepartmans()
   .subscribe(data => {
       this.dataSource = new MatTableDataSource(data);
       this.dataSource.paginator=this.paginator;
       this.dataSource.sort = this.sort;


       this.dataSource.filterPredicate = (data, filter: string) => {
        const accumulator = (currentTerm, key) => {
          return key === 'fakultet' ? currentTerm + data.fakultet.naziv : currentTerm + data[key];
        };
        const dataStr = Object.keys(data).reduce(accumulator, '').toLowerCase();
        const transformedFilter = filter.trim().toLowerCase();
        return dataStr.indexOf(transformedFilter) !== -1;
      };

       // sortiranje po nazivu ugnježdenog objekta
      this.dataSource.sortingDataAccessor = (data, property) => {
        switch (property) {
          case 'fakultet': return data.fakultet.naziv.toLocaleLowerCase();
          default: return data[property];
        }
      };
   }),
   (error:Error) => {
    console.log(error.name + ' ' + error.message);
   }
  }

  public openDialog(flag:number,id?:number, naziv?:string,oznaka?:string,fakultet?:Fakultet){
const dialogRef = this.dialog.open(DepartmanDialogComponent,{data: {id,naziv,oznaka,fakultet}});
dialogRef.componentInstance.flag=flag;

dialogRef.afterClosed()
.subscribe(
  result => {
    if(result === 1){
      this.loadData();
    }
  }
)
  }

  selectRow(row:any){
    this.selektovaniDepartman=row;
  }

  applyFilter(filterValue: string){
    filterValue=filterValue.trim();
    filterValue = filterValue.toLocaleLowerCase();
    this.dataSource.filter = filterValue;
  }

}
