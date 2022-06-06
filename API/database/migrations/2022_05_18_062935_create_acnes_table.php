<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateAcnesTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('acnes', function (Blueprint $table) {
            $table->id();
            $table->string('type')->unique();
            $table->string('name');
            $table->text('description');
            $table->text('cause');
            $table->text('solution');
            $table->text('image');
            $table->text('reference');
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('acnes');
    }
}
