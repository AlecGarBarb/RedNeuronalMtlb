classdef Redneur
   properties
      inputs = 2
      outputs = 1
      hidden = 4
      W1 
      W2
      Z1
      Z2
      Z3
      a2
      yhat
      delta3
      delta2
      djW1
      djW2
      
      
   end
   methods
       function obj =RedNeuronal(obj)
         obj.inputs = 2;
         obj.outputs=1;
         obj.hidden=2;
         obj.W1=rand(obj.inputs , obj.hidden)
         obj.W2 =rand(obj.hidden, obj.outputs)
         return
      end

      function y =sigmoide(obj, Z)
         y =(1 ./ (1 + exp(-Z)))
         return
      end

      function obj =feedForward(obj, X)
         obj.Z2 =  X * obj.W1
         obj.a2 = (1 ./ (1 + exp(-obj.Z2)))   %f(Z2)
         obj.Z3 =  obj.a2 * obj.W2 %a2W2
         obj.yhat =  (1 ./ (1 + exp(-obj.Z3)))  %f(Z3)
         return        
      end
      function y =sigmoideDerivada(obj,Z)
         exp(-Z) ./ ((1+exp(-Z))^2)
         return 
      end

      function costo =funcionCosto(obj,X,Y)
         %Suma de todos los errores, forma de varianza
         %obj = obj.feedForward(X)
         suma = Y - obj.yhat
         costo= 0.5 * sum(suma.^2)
         
         
         return
      end
      function funcionDeCostoDerivada(obj, X, Y)
         obj.feedForward()
         obj.delta3 =  (-(Y-obj.yhat)) * obj.sigmoideDerivada(obj.Z3)
         
         obj.djW2 = obj.a2.' * obj.delta3
         
         obj.delta2 = obj.delta3 * obj.W2.' * self.sigmoideDerivada(self.Z2)
         obj.djW1 =  X.' * obj.delta2
      end
      
      function data = getPesos(obj)
        data = [(reshape(obj.W1.',1,[]))   (reshape(obj.W2.',1,[]))]
        return
      end
      
      function setPesos(obj, datos)
        W1_inicio = 0
        W1_fin = obj.hidden * obj.inputs
        obj.W1 = reshape(datos(W1_inicio:W1_fin) , [obj.inputs , obj.hidden])
        W2_fin = W1_fin + self.hidden*self.outputs
        obj.W2 = reshape(datos(W1_fin:W2_fin), [self.hidden , self.outputs])  
        return
      end
      
      function re =getGradientes(obj, X, y)
        [djW1, djW2] = obj.funcionDeCostoDerivada(X, y)
        
        re = [(reshape(obj.djW1.',1,[]))   (reshape(obj.djW2.',1,[]))]
        return 
          
          
      end
      
   end
end

%Script a ejecutar en terminal
%X = [3,5; 5,1; 10,2] 
%Y = [75; 82;93]
%Xn = X/ max(X(:))
%Yn = Y/100

%rn = RedNeur
%rn = rn.RedNeuronal()
%rn = rn.feedForward()
%error = rn.funcionCosto(Xn,Yn)
%

        