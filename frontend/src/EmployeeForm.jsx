import React, { useState, useEffect } from 'react';
import axios from 'axios'
import './form.css'

const EmployeeForm = () => {
  const [employeeData, setEmployeeData] = useState({
    empId: "",
    firstName: "",
    lastName: "",
    email: "",
    salary: 0.0
  });

  const [error, setError] = useState(null);
  const [invalid, setInValid] = useState(true)

    function isValidEmail(email) {
      if(email.length != 0)
          return /\S+@\S+\.\S+/.test(email);
      return true
    }
    
    const errorStyle = {
      color: "rgb(229, 62, 62)",
      fontSize: "14px",
      marginTop: "7px"
    };



const setFirstName = (e) => {
    setEmployeeData((employeeData) => ({ ...employeeData, firstName: e.target.value }));
  }

  const setLastName = (e) => {
    setEmployeeData((employeeData) => ({ ...employeeData, lastName: e.target.value }));
  }

  const setEmail = (e) => {
    if(!isValidEmail(e.target.value)) {
      setError('Email is invalid')
    } else {
      setError(null);
      setInValid(false)
    }
    setEmployeeData((employeeData) => ({ ...employeeData, email: e.target.value }))
  }

  const setEmpID = (e) => {
    setEmployeeData((employeeData) => ({ ...employeeData, empId: e.target.value }));
  }
  const setSalary = (e) => {
    setEmployeeData((employeeData) => ({ ...employeeData, salary: e.target.value }));
  }


 function saveEmployee (e) {
  if(invalid) return;

  console.log(employeeData);
  e.preventDefault();

    try{
         axios.post(
            `http://localhost:8080/api/v1/employees`,
            {
              empId: employeeData.empId,
              firstName:employeeData.firstName,
              lastName: employeeData.lastName,
              email : employeeData.email,
              salary: employeeData.salary
            }
          )
    }
    catch(err){
        console.log(err)
        console.log(employee.empId + " " + employee.email);
    }
}

  useEffect(() => {
    if(employeeData.empId != "" && employeeData.firstName != "" && employeeData.email != "" && isValidEmail(employeeData.email)) 
        setInValid(false)
    else setInValid(true)
  }, [employeeData.firstName, employeeData.email, employeeData.empId])

  return (
    <div className='formBox'>
      <h2>Employee Details Form</h2>

      <form onSubmit={(e) => saveEmployee(e)}>
      <div className='label'>
      <label>
          Employee ID:
        </label >
        <input 
            type="text" 
            id="empId"
            name="empId" 
            value={employeeData.empId} 
            onChange={e => setEmpID(e)} 
            required 
            className='input'
          />
      </div>
        <br />
        <div className='label'>
        <label>
          First Name:
        </label>
        <input 
            type="text" 
            id="firstName"
            name="firstName" 
            value={employeeData.firstName} 
            onChange={e => setFirstName(e)} 
            required 
            className='input'
          />
        </div>
        <br />
        <div className='label'>
        <label>
          Last Name:
        </label>
        <input 
            type="text" 
            id="lastName"
            name="lastName" 
            value={employeeData.lastName} 
            onChange={e => setLastName(e)} 
            required 
            className='input'
          />
        </div>
        <br />
        <div className='label'>
        <label className='label'>
          Email:
        </label>
        <input 
            type="text" 
            id="email" 
            name="email" 
            value={employeeData.email} 
            onChange={e => setEmail(e)} 
            required 
            className='input'
          />
        </div>
        {error ? <p>{error}</p> : ""}
        <br />
        <div className='label'>
        <label className='label'>
          Salary:
        </label>
        <input 
            type="number" 
            id="salary" 
            name="salary" 
            value={employeeData.salary} 
            onChange={e => setSalary(e)} 
            required 
            className='input'
          />
        </div>
        <br />
        <button type="submit">Submit</button>
      </form>
    </div>
  );
};

export default EmployeeForm;
