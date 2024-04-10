import { useState, useEffect } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import EmployeeForm from './EmployeeForm'
import EmployeeData from './EmployeeData'
import axios from 'axios'

function App() {
  const [employees, setEmployees] = useState([])

  const getCustomers = async () => {
    try{
        return await axios.get(`http://localhost:8080/api/v1/employees`);
    }
    catch (err){
        throw err
    }
  }

  const fetchCustomers = () => {
    getCustomers().then(res => {
      setEmployees(res.data)
      console.log(res.data)
    }).catch(err => {
      console.log(err)
    })
  }

  useEffect(() => {
    fetchCustomers()
  }, [])

  return (
    <>
      <EmployeeForm />
      <EmployeeData data={employees}/>
    </>
  )
}

export default App
