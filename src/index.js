import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

import './index.css';
import PriceList from './pages/PriceList';
import DetailsPrice from './pages/DetailsPrice';
import SearchList from './pages/SearchList';
import reportWebVitals from './reportWebVitals';
import Login from './pages/Login';
import Register from './pages/Register';
import AllTable from './pages/AllTable';
import GetReserve from './pages/GetReserve';
import CreateReserve from './pages/CreateReserve';
import Menu from './pages/Menu';
import Products from './pages/Products';
import Dishes from './pages/Dishes';
import Admin from './pages/Admin';
import Checks from './pages/Checks';

ReactDOM.render(
  <Router>
    <Routes>
      <Route path="/price-list" element={<PriceList />} />
      <Route path="/details/*" element={<DetailsPrice />} />
      <Route path="/search" element={<SearchList />} />
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register/>}/>
      <Route path="/allTable" element={<AllTable />}/>
      <Route path="/getReserve" element={<GetReserve />}/>
      <Route path="/setReserve" element={<CreateReserve />}/>
      <Route path="/menu" element={<Menu />}/>
      <Route path="/products" element={<Products />}/>
      <Route path="/dishes" element={<Dishes />}/>
      <Route path="/cheks" element={<Checks />}/>
      <Route path="/admin" element={<Admin />}/>
      <Route path="*" element={<h1>404 - not found</h1> } />
    </Routes>
  </Router>,
  document.getElementById('root')
);

reportWebVitals();
