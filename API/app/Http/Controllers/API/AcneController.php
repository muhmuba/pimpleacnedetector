<?php
namespace App\Http\Controllers\API;

use App\Http\Controllers\API\BaseController as BaseController;
use App\Models\Acne;
use App\Http\Resources\AcneResource;
use Illuminate\Http\Request;
use Validator;

class AcneController extends BaseController
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $acnes = Acne::all();

        if ($acnes->count() == 0) {
            return $this->sendError('Acne Data Empty');
        }

        return $this->sendResponse(AcneResource::collection($acnes), 'Acne Data retrieved successfully.');
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        $input = $request->all();

        $validator = Validator::make($input, [
            'image'         => 'required|file|image|mimes:jpeg,png,jpg|max:2048',
            'type'          => 'required|unique:acnes',
            'name'          => 'required',
            'description'   => 'required',
            'cause'         => 'required',
            'solution'      => 'required',
            'reference'     => 'required'
        ]);

        if($validator->fails()){
            return $this->sendError('Validation Error.', $validator->errors());
        }

        $image      = $request->file('image');
		$image_name = time()."_".$image->getClientOriginalName();

		$path = 'acne_photos';
		$image->move($path,$image_name);

		$acne = Acne::create([
			'type'          => $request->type,
			'name'          => $request->name,
			'description'   => $request->description,
			'cause'         => $request->cause,
			'solution'      => $request->solution,
			'reference'     => $request->reference,
			'image'         => $image_name,
		]);

        return $this->sendResponse(new AcneResource($acne), 'Acne Data created successfully.');
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        $acne = Acne::find($id);

        if (is_null($acne)) {
            return $this->sendError('Acne Data not found.');
        }

        return $this->sendResponse(new AcneResource($acne), 'Acne Data retrieved successfully.');
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        $input = $request->all();

        $validator = Validator::make($input, [
            'image'         => 'required|file|image|mimes:jpeg,png,jpg|max:2048',
            'type'          => 'required',
            'name'          => 'required',
            'description'   => 'required',
            'cause'         => 'required',
            'solution'      => 'required',
            'reference'     => 'required'
        ]);

        if($validator->fails()){
            return $this->sendError('Validation Error.', $validator->errors());
        }

        $image      = $request->file('image');
		$image_name = time()."_".$image->getClientOriginalName();

		$path = 'acne_photos';
		$image->move($path,$image_name);

        $acne->name         = $input['name'];
        $acne->type         = $input['type'];
        $acne->description  = $input['description'];
        $acne->cause        = $input['cause'];
        $acne->solution     = $input['solution'];
        $acne->reference    = $input['reference'];
        $acne->image        = $image_name;
        $acne->save();

        return $this->sendResponse(new AcneResource($acne), 'Acne Data updated successfully.');
    }

   /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy(Acne $acne)
    {
        $acne->delete();

        return $this->sendResponse([], 'Acne Data deleted successfully.');
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function type(Request $request)
    {
        $type = $request->type;
        $acne = Acne::where('type','=',$type)->first();

        if (is_null($acne)) {
            return $this->sendError('Acne Data not found.');
        }

        return $this->sendResponse(new AcneResource($acne), 'Acne Data retrieved successfully.');
    }

}
