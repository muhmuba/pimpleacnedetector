<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

use App\Http\Controllers\API\RegisterController;
use App\Http\Controllers\API\AcneController;
use App\Http\Controllers\API\ClinicController;
use App\Http\Controllers\API\DatasetController;
/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::controller(RegisterController::class)->group(function(){
    Route::post('register', 'register');
    Route::post('login', 'login');
});

Route::middleware('auth:sanctum')->group( function () {
    //ACNE
    Route::resource('acnes',    AcneController::class);
    Route::get('acne',          [AcneController::class, 'type']);

    //CLINIC
    Route::resource('clinics',  ClinicController::class);

    //Dataset
    Route::post('dataset',      [DatasetController::class,'store']);
});

