<?php

namespace App\Http\Controllers\API;

use App\Http\Controllers\API\BaseController as BaseController;
use App\Helper\Media;
use App\Models\Datasets;
use App\Http\Resources\DatasetResource;
use Illuminate\Http\Request;
use Validator;

class DatasetController extends BaseController
{
    use Media;

    public function store(Request $request)
    {

        $input = $request->all();

        $validator = Validator::make($input, [
            'image'         => 'required|file|image|mimes:jpeg,png,jpg',
            'type'          => 'required|in:normal,blackhead,whitehead,papule,pustules,nodules'
        ]);

        if($validator->fails()){
            return $this->sendError('Validation Error.', $validator->errors());
        }

        if($file = $request->file('image')) {
            $type       = $request->type;
            $path       = "dataset/".$type."/";
            $fileData   = $this->uploads($file,$path);

            $dataset    = Datasets::create([
                'type'  => $request->type,
                'image' => $fileData['filePath']
            ]);

            return $this->sendResponse(new DatasetResource($dataset), 'Dataset upload successfully.');
        }
        else{
            return $this->sendError('File cant upload');
        }
    }
}
