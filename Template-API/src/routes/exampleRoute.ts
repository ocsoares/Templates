import { Router } from 'express';
import { exampleValidation } from '../middleware/exampleValidation';
import { handleValidation } from '../middleware/handleValidation';

const exampleRoute = Router();

exampleRoute.post('/any', exampleValidation(), handleValidation/*, controller... */);

export default exampleRoute;